package molmed.qscripts

import org.broadinstitute.sting.queue.QScript
import org.broadinstitute.sting.queue.util.QScriptUtils
import org.broadinstitute.sting.queue.extensions.picard.MergeSamFiles
import molmed.utils.AlignmentUtils._
import org.broadinstitute.sting.queue.function.ListWriterFunction

class MergeBamsBySample extends QScript {

  qscript =>

  @Input(doc = "List of BAM files", fullName = "input", shortName = "i", required = true)
  var input: File = _

  @Argument(doc = "Output path for the processed BAM files.", fullName = "output_directory", shortName = "outputDir", required = false)
  var outputDir: String = ""

  @Argument(doc = "the project name determines the final output (BAM file) base name. Example NA12878 yields NA12878.processed.bam", fullName = "project", shortName = "p", required = false)
  var projectName: String = ""

  def script() {

    val bams = QScriptUtils.createSeqFromFile(input)

    val sampleNamesAndFiles = for (bam <- bams) yield {
      (getSampleNameFromReadGroups(bam), bam)
    }

    val filesGroupedBySampleName = sampleNamesAndFiles.groupBy(f => f._1).
      mapValues(f => f.map(g => g._2))

    val cohortList =
      for (sampleNamesAndFiles <- filesGroupedBySampleName) yield {

        val sampleName = sampleNamesAndFiles._1
        val mergedFile: File = outputDir + "/" + sampleName + ".bam"
        val files = sampleNamesAndFiles._2

        add(joinBams(files, mergedFile))
        mergedFile
      }

    // output a BAM list with all the processed files
    val cohortFile = new File(outputDir + "/" + projectName + ".cohort.list")
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

}