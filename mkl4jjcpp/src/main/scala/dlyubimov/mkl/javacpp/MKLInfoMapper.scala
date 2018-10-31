package dlyubimov.mkl.javacpp

import org.bytedeco.javacpp.annotation.{Platform, Properties}
import org.bytedeco.javacpp.tools.{Info, InfoMap, InfoMapper}

/**
  * Created by dmitriy on 10/30/18.
  */
@Properties(
  target = "dlyubimov.mkl.javacpp.MKL",
  value = Array(new Platform(
    include = Array(
      /* "mkl.h", */ "mkl_types.h" ,  "mkl_version.h",
      "mkl_service.h", "mkl_blas.h", /* "mkl_cblas.h", */
      "mkl_spblas.h"
    ),
    link = Array(
      "mkl_rt"
      //      "mkl_lapack95_lp64", "mkl_intel_lp64", "mkl_gnu_thread", "mkl_core", "dl", "pthread", "m"
    ),
    compiler = Array("fastfpu", "nodeprecated"),
    library = "jnimkl"
  ))
)
class MKLInfoMapper extends InfoMapper {

  override def map(infoMap: InfoMap): Unit = {
    infoMap
      .put(new Info("MKL_INT").cppTypes().annotations())
      .put(new Info( "MKL_INT64", "MKL_UINT", "MKL_UINT64", "MKL_LONG", "MKL_DECLSPEC", "MKL_CALL_CONV",
        "INTEL_API_DEF", "CBLAS_INDEX", "MKL_UINT8", "MKL_INT8", "MKL_INT16", "MKL_INT32",

        "mkl_get_version", "mkl_get_version_string", "mkl_free_buffers", "mkl_free_buffers",
        "mkl_thread_free_buffers", "mkl_mem_stat", "mkl_peak_mem_usage", "mkl_malloc", "mkl_calloc", "mkl_realloc",
        "mkl_free", "mkl_disable_fast_mm", "mkl_get_cpu_clocks", "mkl_get_cpu_frequency",
        "mkl_get_max_cpu_frequency", "mkl_get_clocks_frequency", "mkl_set_num_threads_local", "mkl_set_num_threads",
        "mkl_get_max_threads", "mkl_set_num_stripes", "mkl_get_num_stripes", "mkl_domain_set_num_thread",
        "mkl_domain_get_max_thread", "mkl_set_dynamic", "mkl_get_dynamic", "mkl_enable_instructions",
        "mkl_set_interface_layer", "mkl_set_threading_layer", "mkl_cbwr_get", "mkl_cbwr_set",
        "mkl_cbwr_get_auto_branch", "mkl_set_env_mode", "mkl_verbose", "mkl_verbose_output_file",
        "mkl_set_exit_handler", "mkl_set_mpi", "mkl_set_memory_limit", "mkl_finalize", "mkl_domain_set_num_threads",
        "mkl_domain_get_max_threads", "mkl_jit_create_sgemm", "mkl_jit_create_dgemm"
      ).cppTypes().annotations())
      //      .put(new Info("MKL_ILP64").define(false))
      .put(new Info("DEPRECATED").cppText("#define DEPRECATED deprecated").cppTypes())
      .put(new Info("MKL_DEPRECATED").cppText("#define MKL_DEPRECATED deprecated").cppTypes())
      .put(new Info("deprecated").annotations("@Deprecated"))
      .put(new Info("sparse_matrix_t").valueTypes("sparse_matrix").pointerTypes("@ByPtrPtr sparse_matrix"))
      .put(new Info("sparse_vector_t").valueTypes("sparse_vector").pointerTypes("@ByPtrPtr sparse_vector"))


    // Skip all functions except for in include list. This is to keep linking lightweight.
    val skipList = allList -- includeList
    infoMap.put(new Info(skipList.toArray: _*).skip())
  }

  val includeList = Set(
    // blas
    "dgemm",
    "dsyrk",
    // sparse
    "mkl_sparse_d_create_csr"
  )

  val allList = Set[String](
    "MKL_SBSRGEMV", "lsame", "mkl_dcsrmm", "MKL_DDIAGEMV", "sgthrz", "dgthrz", "mkl_dcoosm", "stbmv", "MKL_DBSRTRSV",
    "strmm", "DTBSV", "drotm", "MKL_DDIAMV", "daxpy", "MKL_CSPBLAS_DBSRTRSV", "DGEMM", "mkl_scsradd", "MKL_DCSCMM",
    "MKL_DCSRSYMV", "dtrmv", "sroti", "DSPR2", "mkl_sbsrsm", "STPMV", "SSYMV", "mkl_cspblas_dbsrtrsv", "MKL_SSKYSV",
    "dtbsv", "MKL_DCSCSV", "ssyr", "DSPMV", "mkl_cspblas_scsrsymv", "mkl_sparse_d_set_value", "MKL_SCSRCSC",
    "MKL_DCOOGEMV", "mkl_sparse_set_sm_hint", "mkl_dcsrcsc", "mkl_ddiatrsv", "MKL_SCOOSM", "scopy", "mkl_dcoomv",
    "sswap", "DGEMV", "MKL_DDIATRSV", "srotg", "isamin", "sspmv", "DASUM", "mkl_scoosv", "dgbmv", "mkl_sparse_d_add",
    "mkl_scsrmv", "mkl_sparse_s_create_csc", "SGEMM", "mkl_ddiasm", "MKL_DCSRMV", "MKL_DBSRSYMV", "MKL_DCOOMM",
    "mkl_scscsv", "DTPMV", "MKL_DDIAMM", "MKL_CSPBLAS_SCOOGEMV", "MKL_SCSRMULTCSR", "mkl_sparse_s_add", "DTRMV",
    "DSPR", "mkl_sparse_d_mv", "mkl_dcsrmultcsr", "mkl_cspblas_dcootrsv", "SROTG", "MKL_SDIATRSV",
    "mkl_cspblas_scoogemv", "DROT", "mkl_scscmv", "MKL_DCOOSYMV", "mkl_scsrcoo", "DGER", "SGTHR", "mkl_scsrdia",
    "SSYR2K", "DAXPYI", "DSYR2", "mkl_sparse_s_trsv", "mkl_sparse_copy", "SDOT", "DGEMM_BATCH", "SSPMV", "saxpyi",
    "IDAMAX", "MKL_DCSRSM", "mkl_cspblas_scoosymv", "MKL_CSPBLAS_SCOOTRSV", "MKL_SCOOSYMV", "mkl_dcsrdia", "SSPR",
    "STRMM", "dgemmt", "MKL_SBSRSYMV", "mkl_scsrsm", "DSBMV", "MKL_SCSCMM", "MKL_DSKYSM", "dgemm", "srotmg",
    "saxpby", "mkl_dcoosymv", "SAXPBY", "mkl_sskymm", "MKL_DCSRSV", "MKL_CSPBLAS_DCOOSYMV", "sdoti", "mkl_dbsrsm",
    "mkl_cspblas_sbsrtrsv", "sdot", "mkl_dbsrtrsv", "mkl_dcsrmultd", "MKL_SBSRMV", "MKL_SSKYMM", "IDAMIN",
    "mkl_cspblas_dcoosymv", "mkl_dcsrsv", "SASUM", "SDOTI", "SAXPYI", "dtrmm", "ssyr2k", "mkl_dcoogemv",
    "mkl_sskymv", "SGEMV", "MKL_CSPBLAS_SCSRTRSV", "MKL_SCSCSM", "mkl_sparse_d_create_coo", "dsymm", "sgemmt",
    "MKL_DBSRMV", "mkl_dbsrmm", "MKL_SSKYSM", "mkl_sparse_spmm", "MKL_CSPBLAS_DCSRTRSV", "MKL_SBSRTRSV",
    "MKL_DDIASYMV", "MKL_DCSRTRSV", "MKL_SBSRMM", "MKL_SCSRSKY", "mkl_scoomm", "mkl_sbsrsv", "stbsv", "DAXPBY",
    "sgthr", "mkl_ddiamv", "MKL_DCSRADD", "MKL_DCSRGEMV", "DDOTI", "MKL_CSPBLAS_DCSRGEMV", "MKL_SDIAGEMV",
    "mkl_cspblas_dcsrsymv", "DAXPY", "DTBMV", "STRMV", "mkl_sparse_set_memory_hint", "MKL_SBSRSM",
    "mkl_cspblas_sbsrsymv", "mkl_sbsrtrsv", "mkl_sparse_s_mv", "mkl_dcsrmv", "stpmv", "ssbmv", "sger",
    "mkl_sparse_set_mv_hint", "MKL_DSKYMV", "MKL_SCSRSV", "MKL_DCSRCOO", "SGBMV", "dtbmv", "MKL_SDIASV", "dnrm2",
    "mkl_dskymv", "mkl_sparse_d_mm", "SAXPY", "DSCAL", "mkl_cspblas_scsrtrsv", "mkl_dcoosv", "mkl_dcsrsky",
    "MKL_CSPBLAS_SCOOSYMV", "MKL_SCSRCOO", "sgbmv", "dspr", "mkl_sdiamm", "mkl_sparse_s_export_bsr", "mkl_scsrsv",
    "mkl_scsrgemv", "MKL_SCSCSV", "mkl_sdiagemv", "MKL_SBSRSV", "SGER", "DSYMM", "dsctr", "SSYMM", "mkl_dbsrmv",
    "MKL_DCSRDIA", "dasum", "MKL_DCOOTRSV", "mkl_sparse_s_mm", "SSBMV", "stpsv", "idamin", "dsbmv", "sgem2vu",
    "dscal", "mkl_scsrbsr", "mkl_dskysv", "dsymv", "MKL_DDNSCSR", "DSYR", "ssymv", "dtrsv", "mkl_sparse_s_trsm",
    "mkl_dbsrgemv", "MKL_DDIASM", "MKL_DCSRMULTD", "mkl_sparse_d_spmmd", "mkl_sparse_convert_csr", "SDSDOT",
    "mkl_dcsrsymv", "mkl_sparse_get_error_info", "MKL_CSPBLAS_SBSRSYMV", "SSYR", "ssyr2", "mkl_scoosymv",
    "MKL_DSKYSV", "mkl_ddnscsr", "sscal", "MKL_DBSRMM", "MKL_SDIASYMV", "mkl_sdiasv", "mkl_dcoomm", "mkl_scscmm",
    "mkl_scsrtrsv", "mkl_sparse_destroy", "SSWAP", "MKL_CSPBLAS_SBSRGEMV", "ssctr", "LSAME", "dgem2vu", "dtpsv",
    "DTPSV", "MKL_SCOOTRSV", "drot", "mkl_sparse_d_export_csr", "xerbla", "MKL_CSPBLAS_SCSRGEMV", "snrm2", "SGEMMT",
    "DGTHRZ", "mkl_sparse_s_spmmd", "mkl_ddiasymv", "MKL_DCSCSM", "dtrsm", "mkl_scoosm", "mkl_scsrcsc", "MKL_DCOOMV",
    "MKL_DCSRCSC", "SSYR2", "mkl_sskysv", "dsyrk", "DTRMM", "SROTI", "mkl_cspblas_dbsrsymv", "SNRM2", "mkl_sbsrgemv",
    "mkl_scsrsky", "DGBMV", "SROT", "mkl_cspblas_dcsrtrsv", "mkl_scootrsv", "mkl_sdiasymv", "DTRSV", "SGEM2VU",
    "MKL_CSPBLAS_DCOOTRSV", "srotm", "SGEMM_BATCH", "mkl_dbsrsymv", "MKL_CSPBLAS_DCSRSYMV", "MKL_SDIAMM",
    "mkl_dcscsv", "mkl_dcsrbsr", "DROTM", "MKL_CSPBLAS_DCOOGEMV", "sasum", "MKL_SCOOSV", "DSYRK", "DSDOT",
    "mkl_cspblas_dcoogemv", "mkl_sparse_d_trsv", "daxpby", "STRSM", "mkl_scsrsymv", "mkl_sparse_d_create_bsr",
    "mkl_ddiasv", "dsyr2", "mkl_sparse_set_mm_hint", "mkl_sparse_s_export_csr", "mkl_scoogemv", "MKL_SDIASM",
    "MKL_DCSCMV", "MKL_CSPBLAS_SBSRTRSV", "SROTM", "mkl_sskysm", "mkl_sparse_s_set_value", "dgemv", "DROTG", "dgthr",
    "mkl_cspblas_dcsrgemv", "SCOPY", "mkl_cspblas_scsrgemv", "SSPR2", "MKL_DCOOSV", "DGTHR", "MKL_SDIAMV", "DNRM2",
    "MKL_DCSRSKY", "MKL_SCSRDIA", "droti", "mkl_dskysm", "mkl_dcscsm", "mkl_sparse_d_create_csc", "SSCTR",
    "MKL_SCSRMM", "dspr2", "mkl_dcootrsv", "DTRSM", "mkl_ddiagemv", "MKL_SCOOMV", "DDOT", "dtpmv", "SROTMG", "sgemm",
    "mkl_cspblas_dbsrgemv", "mkl_scsrmm", "MKL_CSPBLAS_DBSRGEMV", "MKL_CSPBLAS_SCSRSYMV", "mkl_dcsrsm", "mkl_scoomv",
    "SGTHRZ", "strsv", "MKL_SCSRSYMV", "sspr2", "drotg", "DSCTR", "MKL_DCSRMULTCSR", "mkl_sbsrmv", "sspr",
    "MKL_SSKYMV", "dsyr2k", "DSYMV", "MKL_DCOOSM", "ddot", "STRSV", "dcopy", "SSYRK", "DSWAP", "mkl_dcsrgemv",
    "XERBLA", "mkl_sdiatrsv", "mkl_sparse_set_verbose_mode", "MKL_SCSRMULTD", "MKL_DBSRSV", "DCOPY", "srot",
    "DSYR2K", "mkl_sdnscsr", "MKL_DBSRGEMV", "dsyr", "mkl_cspblas_scootrsv", "mkl_dcsradd", "MKL_SCSRSM", "DGEM2VU",
    "mkl_sdiamv", "ISAMAX", "STPSV", "MKL_DCSRMM", "mkl_scsrmultcsr", "MKL_SCOOMM", "MKL_SCSRTRSV",
    "mkl_sparse_optimize", "ssymm", "mkl_sparse_s_create_csr", "MKL_CSPBLAS_DBSRSYMV", "MKL_SCSCMV", "mkl_sdiasm",
    "mkl_ddiamm", "saxpy", "DROTMG", "mkl_sparse_d_export_bsr", "mkl_dcscmv", "MKL_SDNSCSR", "mkl_sparse_d_trsm",
    "MKL_SCSRGEMV", "STBMV", "mkl_sbsrmm", "drotmg", "MKL_SCOOGEMV", "MKL_DDIASV", "SSCAL", "DROTI", "mkl_scscsm",
    "DGEMMT", "dswap", "MKL_SCSRMV", "MKL_DCSRBSR", "idamax", "mkl_dbsrsv", "MKL_DBSRSM", "sgemv", "strmv",
    "mkl_sparse_s_create_coo", "sdsdot", "strsm", "mkl_scsrmultd", "mkl_sparse_convert_bsr", "ssyrk", "mkl_dcsrtrsv",
    "ddoti", "daxpyi", "mkl_sparse_set_sv_hint", "mkl_dcscmm", "STBSV", "mkl_sparse_d_create_csr", "sgemm_batch",
    "dger", "ISAMIN", "MKL_DSKYMM", "dgemm_batch", "mkl_sparse_s_create_bsr", "isamax", "mkl_sbsrsymv",
    "MKL_SCSRBSR", "dspmv", "mkl_dcsrcoo", "mkl_cspblas_sbsrgemv", "mkl_dskymm", "MKL_SCSRADD", "dsdot",

    // New stuff in the 2018 update 3
    "GEMM_S8U8S32",
    "GEMM_S8U8S32",
    "GEMM_S8U8S32",
    "GEMM_S8U8S32",
    "GEMM_S8U8S32",
    "GEMM_S8U8S32",
    "GEMM_S16S16S32",
    "GEMM_S16S16S32",
    "GEMM_S16S16S32",
    "GEMM_S16S16S32",
    "GEMM_S16S16S32",
    "GEMM_S16S16S32",

    "gemm_s16s16s32",
    "gemm_s16s16s32",
    "gemm_s16s16s32",
    "gemm_s16s16s32",
    "gemm_s16s16s32",
    "gemm_s16s16s32",
    "gemm_s8u8s32",
    "gemm_s8u8s32",
    "gemm_s8u8s32",
    "gemm_s8u8s32",
    "gemm_s8u8s32",
    "gemm_s8u8s32",

    "mkl_sparse_s_syprd", "mkl_sparse_d_sp2md", "sgemm_pack", "dgemm_alloc", "mkl_sparse_d_symgs",
    "mkl_sparse_s_symgs", "strsm_batch", "mkl_sparse_sypr", "mkl_sparse_d_dotmv", "mkl_sparse_s_symgs_mv",
    "SGEMM_PACK", "DGEMM_ALLOC", "dgemm_compute", "SGEMM_COMPUTE", "mkl_sparse_d_export_csc",
    "mkl_sparse_sp2m", "mkl_sparse_d_symgs_mv", "DTRSM_BATCH", "DGEMM_COMPUTE", "SGEMM_ALLOC",
    "DGEMM_FREE", "sgemm_alloc", "mkl_sparse_set_dotmv_hint", "mkl_sparse_d_syprd", "mkl_sparse_s_dotmv",
    "SGEMM_FREE", "sgemm_free", "mkl_sparse_s_sp2md", "STRSM_BATCH", "sgemm_compute",
    "dgemm_free", "mkl_sparse_set_symgs_hint", "dgemm_pack", "dtrsm_batch", "DGEMM_PACK",
    "mkl_sparse_d_syrkd", "mkl_sparse_s_export_csc", "mkl_sparse_s_syrkd"


  )
}
