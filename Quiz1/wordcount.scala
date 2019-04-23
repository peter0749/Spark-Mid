import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
object WordCount {
	def main(args: Array[String]): Unit= {
		val inputFile = args(0)
		val sc = new SparkContext()
		// Load our input data.
		var input = sc.textFile(inputFile)
        // Filter out (".,:;()!?*$\[]-`')
        val special = """\"\'\`\(\)\[\],.:;?!*$\-"""
        val cleaned_input = input.map(x => x.replaceAll(special, ""))
		// Split up into words.
		val words = cleaned_input.flatMap(line => line.split(" "))
		// Transform into word and count.
		val counts = words.map(word => (word, 1)).reduceByKey{case
			(x, y) => x + y
		}.sortBy(x=>x._2, false)
        val top_counts = counts.zipWithIndex.filter(x => x._2<=30).map(x => x._1)
        top_counts.foreach(println)
	}
}

