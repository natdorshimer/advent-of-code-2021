package advent.of.code



//Makes it easy and safe to read files - but only available from object context
inline fun <reified T> T.readFileToString(fileName: String, clazz: Class<T> = T::class.java): String? =
  fileName.let{
    if (it.startsWith("/")) it else "/$it"
  }.let{
    clazz.getResource(it)?.readText()
  }