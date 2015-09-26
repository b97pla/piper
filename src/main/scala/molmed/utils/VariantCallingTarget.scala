package molmed.utils

import java.io.File

/**
 * Help class handling each variant calling target. Storing input files, creating output filenames etc.
 */
class VariantCallingTarget(outputDir: File,
                           val baseName: String,
                           val reference: File,
                           val bamList: Seq[File],
                           val intervals: Option[File],
                           val isLowpass: Boolean,
                           val isExome: Boolean,
                           val nSamples: Int,
                           val snpGenotypingVcf: Option[File] = None,
                           val vcfExtension: String = "vcf") {

  val vcfExt = if (vcfExtension.startsWith(".")) vcfExtension.tail else vcfExtension
  val name = outputDir + "/" + baseName
  val clusterFile = new File(name + ".clusters")
  val gVCFFile = new File(name + ".genomic." + vcfExt)
  val rawSnpVCF = new File(name + ".raw.snp." + vcfExt)
  val rawIndelVCF = new File(name + ".raw.indel." + vcfExt)
  val rawCombinedVariants = new File(name + ".raw." + vcfExt)
  val filteredIndelVCF = new File(name + ".filtered.indel." + vcfExt)
  val recalibratedSnpVCF = new File(name + ".recalibrated.snp." + vcfExt)
  val recalibratedIndelVCF = new File(name + ".recalibrated.indel." + vcfExt)
  val tranchesSnpFile = new File(name + ".snp.tranches")
  val tranchesIndelFile = new File(name + ".indel.tranches")
  val vqsrSnpRscript: File = new File(name + ".snp.vqsr.r")
  val vqsrIndelRscript: File = new File(name + ".indel.vqsr.r")
  val recalSnpFile = new File(name + ".snp.tranches.recal")
  val recalIndelFile = new File(name + ".indel.tranches.recal")
  val evalFile = new File(name + ".snp.eval")
  val evalIndelFile = new File(name + ".indel.eval")
  val genotypeConcordance = new File(name + ".genotypeconcordance.txt")
}
