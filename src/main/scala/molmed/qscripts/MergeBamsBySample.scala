package molmed.qscripts

import org.broadinstitute.sting.queue.QScript
import org.broadinstitute.sting.queue.util.QScriptUtils
import org.broadinstitute.sting.queue.extensions.picard.MergeSamFiles
import molmed.utils.AlignmentUtils._
import org.broadinstitute.sting.queue.function.ListWriterFunction
import org.broadinstitute.sting.queue.function.InProcessFunction

class MergeBamsBySample extends QScript {

  qscript =>

  @Input(doc = "List of BAM files", fullName = "input", shortName = "i", required = true)
  var input: File = _

  @Argument(doc = "Output path for the processed BAM files.", fullName = "output_directory", shortName = "outputDir", required = false)
  var outputDir: String = ""
  def getOutputDir: String = if (outputDir.isEmpty()) "" else outputDir + "/"

  @Argument(doc = "the project name determines the final output (BAM file) base name. Example NA12878 yields NA12878.processed.bam", fullName = "project", shortName = "p", required = false)
  var projectName: String = ""

  def script() {

    val bams = QScriptUtils.createSeqFromFile(input)

    val sampleNamesAndFiles = for (bam <- bams) yield {
      (getSampleNameFromReadGroups(bam), bam)
    }

    val filesGroupedBySampleName =
      sampleNamesAndFiles.
        groupBy(f => f._1).
        mapValues(f => f.map(g => g._2))

    val cohortList =
      for (sampleNamesAndFiles <- filesGroupedBySampleName) yield {

        val sampleName = sampleNamesAndFiles._1
        val mergedFile: File = getOutputDir + sampleName + ".bam"
        val files = sampleNamesAndFiles._2

        // If there is only on file associated with the sample name, just create a
        // hard link instead of merging.
        if (files.size > 1) {
          add(joinBams(files, mergedFile))
          mergedFile
        }
        else {
          add(createLink(files(0), mergedFile))
          mergedFile
        }
      }

    // output a BAM list with all the processed files
    val cohortFile = new File(getOutputDir + projectName + ".cohort.list")
    add(writeList(cohortList.toSeq, cohortFile))

  }

  // General arguments to non-GATK tools
  trait ExternalCommonArgs extends CommandLineFunction {
    this.memoryLimit = 24
    this.isIntermediate = false
  }

  case class joinBams(inBams: Seq[File], outBam: File) extends MergeSamFiles with ExternalCommonArgs {
    this.input = inBams
    this.output = outBam
    this.analysisName = "joinBams" + outBam.getName()
    this.jobName = "joinBams" + outBam.getName()
  }

  case class writeList(inBams: Seq[File], outBamList: File) extends ListWriterFunction {
    this.inputFiles = inBams
    this.listFile = outBamList
    this.analysisName = "bamList"
    this.jobName = "bamList"
  }

  case class createLink(@Input inBam: File, @Output outBam: File) extends InProcessFunction {

    def run() {

      import scala.sys.process.Process

      val linkProcess = Process("""ln """ + inBam.getAbsolutePath() + """ """ + outBam.getAbsolutePath())
      val exitCode = linkProcess.!
      assert(exitCode == 0, "Couldn't create hard link from: " + inBam.getAbsolutePath() + " to: " + outBam.getAbsolutePath())
    }

  }

}