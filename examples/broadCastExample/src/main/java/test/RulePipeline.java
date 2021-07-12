package test;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.BroadcastStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.util.Collector;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.functions.MapFunction;

import test.RuleEvaluator;

public class RulePipeline {

    public static void main(String[] args) throws Exception {

    
        
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String>  ruleStream = env.socketTextStream("localhost",9991);
        DataStream<Integer> dataStream = env.socketTextStream("localhost",9992)
                                            .map(new MapFunction<String, Integer>(){
                                                @Override
                                                public Integer map(String value) throws Exception {
                                                    return Integer.parseInt(value);
                                                }
                                            });

        MapStateDescriptor<String, String> ruleStateDescriptor = new MapStateDescriptor<>(
            "RulesBroadcastState",
            BasicTypeInfo.STRING_TYPE_INFO,
            BasicTypeInfo.STRING_TYPE_INFO
            );


        BroadcastStream<String> ruleBroadcastStream = ruleStream.broadcast(ruleStateDescriptor);


        DataStream<String> output = dataStream.connect(ruleBroadcastStream)
                                              .process(new RuleEvaluator());


        

        output.writeAsText("test.txt");

        env.execute("WindowRuleMatching");
        
    }

    public static class Splitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String sentence, Collector<Tuple2<String, Integer>> out) throws Exception {
            for (String word: sentence.split(" ")) {
                out.collect(new Tuple2<String, Integer>(word, 1));
            }
        }
    }

}

