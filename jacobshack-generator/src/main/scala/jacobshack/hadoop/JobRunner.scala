package jacobshack.hadoop

/**
 * @author Andreas C. Osowski
 */

import com.twitter.scalding.Tool
import org.apache.hadoop
import org.apache.hadoop.mapred.lib.MultithreadedMapRunner

object JobRunner {
  def main(args: Array[String]) {
    val conf = new hadoop.conf.Configuration

    conf.set("mapreduce.job.jvm.numtasks", "7")
    conf.set("mapred.tasktracker.tasks.maximum", "7")
    conf.set("mapred.tasktracker.map.tasks.maximum", "3")
    conf.set("mapred.map.multithreadedrunner.threads", "7")
    conf.set("mapred.tasktracker.reduce.tasks.maximum", "4")

    hadoop.util.ToolRunner.run(conf, new Tool, args);
  }
}
