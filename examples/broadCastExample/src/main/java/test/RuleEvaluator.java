package test;

import org.apache.flink.api.common.state.BroadcastState;
import org.apache.flink.api.common.state.ReadOnlyBroadcastState;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.metrics.Gauge;
import org.apache.flink.streaming.api.functions.co.BroadcastProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;


public class RuleEvaluator extends BroadcastProcessFunction<Integer,String,String> {

    
    @Override
    public void processElement(Integer event,
                                 ReadOnlyContext ctx,
                               Collector<String> out)
    {
        System.out.println("The event="+event);
    }

    @Override
    public void processBroadcastElement(String rule, Context ctx, Collector<String> out)
    {
        System.out.println("The rule="+rule);
    }
}
