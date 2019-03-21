import java.io.File

object PathExtractor {

  def getListOfFiles(dir: String):List[String] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).map(file => file.getAbsolutePath).toList
    } else {
      List[String]()
    }
  }

}
