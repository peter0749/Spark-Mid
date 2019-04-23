import java.io.File
import java.io.PrintWriter
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
object MM {
	def main(args: Array[String]): Unit= {
		val input_A = args(0)
        val input_B = args(1)
        val output  = args(2)
		val sc = new SparkContext()
		// Load our input data.
		var a = sc.textFile(input_A).map(line => line.split(",").map( x => x.toDouble ).toArray).toArray
		var b = sc.textFile(input_B).map(line => line.split(",").map( x => x.toDouble ).toArray).toArray
        // var a = Array(Array(1,2))
        // var b = Array(Array(3,4,5), Array(6,7,8))
        var v = sc.makeRDD(Array[((Int,Int,Int),Double)]())
        val m = a.length
        val n = a(0).length
        val p = b(0).length
        for (i<-0 until m; k<-0 until p) {
            for (j<-0 until n) {
                val v1 = sc.makeRDD(Array[((Int,Int,Int),Double)](((i,j,k), a(i)(j))))
                val v2 = sc.makeRDD(Array[((Int,Int,Int),Double)](((i,j,k), b(j)(k))))
                v = v.union(v1).union(v2)
            }
        }
        val mulRDD = v.reduceByKey(_*_).map{case ((i,j,k),v) => ((i,k),v)}
        val result = mulRDD.reduceByKey(_+_).collectAsMap
        val writer = new PrintWriter(new File(output))
        for (i<-0 until m; k<-0 until p) {
            if (k==0) writer.write("\n");
            writer.write("%1.2f ".format(result((i,k))))
        }
        writer.close()
	}
}

