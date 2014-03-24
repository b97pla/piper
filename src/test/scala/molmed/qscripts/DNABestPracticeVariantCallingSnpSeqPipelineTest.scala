package molmed.qscripts

import org.testng.annotations._
import molmed.queue.SnpSeqBaseTest
import org.broadinstitute.sting.queue.pipeline._

class DNABestPracticeVariantCallingSnpSeqPipelineTest {

  val pathToScript = "-S src/main/scala/molmed/qscripts/DNABestPracticeVariantCalling.scala"

  val snpSeqBaseTest = SnpSeqBaseTest

  var run: Boolean = false

  val resourceBasePath = "/local/data/gatk_bundle/b37/"

  @BeforeClass
  @Parameters(Array("runpipeline"))
  def init(runpipeline: Boolean): Unit = {
    this.run = runpipeline
  }

  @Test()
  def testWholeGenome {
    //@TODO Specify pipeline outputs. 

    val co1156bam = "pipeline_output/processed_alignments/Co1156.clean.dedup.recal.bam"
    val co454bam = "pipeline_output/processed_alignments/Co454.clean.dedup.recal.bam"
    val rawSnps = "pipeline_output/variant_calls/piper_test.raw.snp.vcf"
    val rawIndels = "pipeline_output/variant_calls/piper_test.raw.indel.vcf"

    val spec = new PipelineTestSpec()
    spec.jobRunners = Seq("Shell")
    spec.name = "wgs_bestpracticevariantcalling"
    spec.args = Array(
      pathToScript,
      " --xml_input " + "/local/data/pipelineTestFolder/pipelineSetup.xml",
      " --dbsnp " + resourceBasePath + "dbsnp_137.b37.vcf",
      " --extra_indels " + resourceBasePath + "Mills_and_1000G_gold_standard.indels.b37.vcf",
      " --extra_indels " + resourceBasePath + "1000G_phase1.indels.b37.vcf",
      " --hapmap " + resourceBasePath + "hapmap_3.3.b37.vcf",
      " --omni " + resourceBasePath + "1000G_omni2.5.b37.vcf",
      " --mills " + resourceBasePath + "Mills_and_1000G_gold_standard.indels.b37.vcf",
      " -bwa " + "~/Bin/bwa-0.7.5a/bwa",
      " -samtools " + "~/Bin/samtools-0.1.19/samtools",
      " --number_of_threads 1 ",
      " --skip_recalibration ",
      " --scatter_gather 1 ",
      " -test ",
      " -startFromScratch "
      ).mkString
    spec.fileMD5s += co1156bam -> "c5b7787eb82eac7aaa6b359851561cd4"
    spec.fileMD5s += co454bam -> "122eef66ce52b5a1a178991813a8e1e3"
    spec.fileMD5s += rawSnps -> "4825361e107c122a84b1f48647fee071"
    spec.fileMD5s += rawIndels -> "c48dfabccf1665df64c541623727427b"
    spec.run = this.run
    PipelineTest.executeTest(spec)
  }

  @Test()
  def testExome {

    val co1156bam = "pipeline_output/processed_alignments/Co1156.clean.dedup.recal.bam"
    val co454bam = "pipeline_output/processed_alignments/Co454.clean.dedup.recal.bam"
    val rawSnps = "pipeline_output/variant_calls/piper_test.raw.snp.vcf"
    val rawIndels = "pipeline_output/variant_calls/piper_test.raw.indel.vcf"
      
    val spec = new PipelineTestSpec()
    spec.jobRunners = Seq("Shell")
    spec.name = "exome_bestpracticevariantcalling"
    spec.args = Array(
      pathToScript,
      " --xml_input " + "/local/data/pipelineTestFolder/pipelineSetup.xml",
      " --isExome ",
      " --gatk_interval_file " + "/local/data/pipelineTestFolder/TruSeq_exome_targeted_regions-gatk.interval_list",
      " --dbsnp " + resourceBasePath + "dbsnp_137.b37.vcf",
      " --extra_indels " + resourceBasePath + "Mills_and_1000G_gold_standard.indels.b37.vcf",
      " --extra_indels " + resourceBasePath + "1000G_phase1.indels.b37.vcf",
      " --hapmap " + resourceBasePath + "hapmap_3.3.b37.vcf",
      " --omni " + resourceBasePath + "1000G_omni2.5.b37.vcf",
      " --mills " + resourceBasePath + "Mills_and_1000G_gold_standard.indels.b37.vcf",
      " -bwa " + "~/Bin/bwa-0.7.5a/bwa",
      " -samtools " + "~/Bin/samtools-0.1.19/samtools",
      " --skip_recalibration ",
      " --number_of_threads 1 ",
      " --scatter_gather 1 ",
      " -test ",
      " -startFromScratch "
      ).mkString

    spec.fileMD5s += co1156bam -> "634d77c4a48bd066d4697d56f0a4e16e"
    spec.fileMD5s += co454bam -> "a3b9e64cba88bbd12e4e5f2a5f0ea9a7"
    spec.fileMD5s += rawSnps -> "dc4f26db5aeb15bbe043695f241b260f"
    spec.fileMD5s += rawIndels -> "0521e600245245ba2745699c45a0f93d"
    spec.run = this.run
    PipelineTest.executeTest(spec)
  }

}
