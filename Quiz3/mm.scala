import java.io.File
import java.io.PrintWriter
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
object MM {
	def main(args: Array[String]): Unit= {
		val input_A = args(0)
		val sc = new SparkContext()
		// Load our input data.
		var trans = sc.makeRDD(sc.textFile(input_A).map(line => line.split(",").map( x => x.replaceAll("\"", "") ).toArray).toArray)
        val allComb=trans.map{t=>
            for(i<-1 to t.length) yield {
                val eleCom=t.combinations(i)
                val kv=eleCom.map(ele=>"("+ele.sorted.mkString(",")+")")
                kv
            }
        }.flatMap(x=>x).flatMap(x=>x)
        allComb.map(x => (x,1)).reduceByKey(_+_).sortBy(x=>x._2,false).foreach(println)
	}
}

