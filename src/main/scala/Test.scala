import org.apache.spark.{SparkConf, SparkContext}
import org.elasticsearch.spark._
import sys.process._





object Test {
  def main(args: Array[String]) {


/* Spark Configuration */

    val conf = new SparkConf()
      .setAppName("CV Extractor")
      .setMaster("local[*]")
      .set("es.nodes","localhost:9200")
      .set("es.index.auto.create", "true")


/*Spark Context Init */
    val sc = new SparkContext(conf)


/* Extract List of files from folder */
    val files = PathExtractor.getListOfFiles(args(0))

    val pathList = files.map(x => x.split(".pdf")(0))


    var x = Seq[String]()


/* Extract CV Content from files */
    pathList.foreach(file => {
      println("executing cvscan for file: " + file)

      val result = "cvscan parse --name " + file !!

      val validJson = result.substring(1, result.length - 2)

      x ++= Seq(validJson)
              } )

/* Save To ElasticSearch */
    sc.makeRDD(x)
      .saveJsonToEs("aliblog/json")



}}




