package SF;


import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class Signal implements Serializable
{
    private String name;
    private Double amplitude;
    private Double period;
    private Double startingTime;
    private Double durationTime;
    private Double fulfillment;
    private Integer sampling;
    private Map<Double, Double> values;
    private Map<Double, Double> imaginaryValues;
    private int[] val;

    public Signal(){

	}

    public Signal(String name)
    {
        this.name = name;
        this.values = new HashMap<Double, Double>();
        this.imaginaryValues = new HashMap<Double, Double>();
    }

    public Signal(String name, Double amplitude, Double period, Double startingTime, Double durationTime, Double fulfillment, Integer sampling)
    {
        this.name = name;
        this.amplitude = amplitude;
        this.period = period;
        this.startingTime = startingTime;
        this.durationTime = durationTime;
        this.fulfillment = fulfillment;
        this.sampling = sampling;
        this.values = new HashMap<Double, Double>();
        this.imaginaryValues = new HashMap<Double, Double>();
    }

    public void setValue(int value, int index){
    	val[index] = value;
	}

	public void copy(Signal signal)
    {
        this.name = signal.getName();
        this.amplitude = signal.getAmplitude();
        this.period = signal.getPeriod();
        this.startingTime = signal.getStartingTime();
        this.durationTime = signal.getDurationTime();
        this.fulfillment = signal.getFulfillment();
        this.sampling = signal.getSampling();
        this.values = signal.getValues();
        this.imaginaryValues = signal.getImaginaryValues();
        this.val = signal.getVal();
    }


}
