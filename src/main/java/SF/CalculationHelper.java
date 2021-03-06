package SF;

import SF.GUI.AlertBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CalculationHelper {
	public Double Average(Signal signal) {
		Double result = 0.0;

		double k = 0;
		int size = signal.getValues().size();
		if (signal.getPeriod() != null) {
			k = signal.getDurationTime() / signal.getPeriod();
			size = (int)k * signal.getSampling();

		}


		int i = 0;
		for (Map.Entry<Double, Double> entry : signal.getValues().entrySet()) {
			if (i <= size) {
				result += entry.getValue();
			}
			i++;
		}

		result = result / (double) (size + 1);

		result *= 100;
		result = (double) Math.round(result);
		result /= 100;

		return result;
	}

	public Double AbsoluteAverage(Signal signal) {
		Double result = 0.0;

		double k = 0;
		int size = signal.getValues().size();
		if (signal.getPeriod() != null) {
			k = signal.getDurationTime() / signal.getPeriod();
			size = (int)k * signal.getSampling();

		}
		int i = 0;
		for (Map.Entry<Double, Double> entry : signal.getValues().entrySet()) {
			if (i < size) {
				result += Math.abs(entry.getValue());
			}
			i++;
		}

		result = result / (double) (size + 1);

		result *= 100;
		result = (double) Math.round(result);
		result /= 100;

		return result;
	}

	public Double Strength(Signal signal) {
		Double result = 0.0;

		double k = 0;
		int size = signal.getValues().size();
		if (signal.getPeriod() != null) {
			k = signal.getDurationTime() / signal.getPeriod();
			size = (int)k * signal.getSampling();

		}
		int i = 0;
		for (Map.Entry<Double, Double> entry : signal.getValues().entrySet()) {
			if (i < size) {
				result += entry.getValue() * entry.getValue();
			}
			i++;
		}

		result = result / (double) (size + 1);

		result *= 100;
		result = (double) Math.round(result);
		result /= 100;

		return result;
	}

	public Double Variance(Signal signal) {
		Double result = 0.0;
		Double average = this.Average(signal);

		double k = 0;
		int size = signal.getValues().size();
		if (signal.getPeriod() != null) {
			k = signal.getDurationTime() / signal.getPeriod();
			size = (int)k * signal.getSampling();

		}
		int i = 0;
		for (Map.Entry<Double, Double> entry : signal.getValues().entrySet()) {
			if (i < size) {
				result += Math.pow(entry.getValue() - average, 2);
			}
			i++;
		}

		result = result / (double) (size + 1);

		result *= 100;
		result = (double) Math.round(result);
		result /= 100;

		return result;
	}

	public Double RootMeanSquare(Signal signal) {
		Double result = 0.0;
		result = Math.sqrt(this.Strength(signal));

		result *= 100;
		result = (double) Math.round(result);
		result /= 100;

		return result;
	}


	public Signal addSignals(Signal signalOne, Signal signalTwo) throws WrongSamplingException {
		if (!signalOne.getSampling().equals(signalTwo.getSampling())) {
			throw new WrongSamplingException("Sampling of two signals does't match");
		}

		List<Double> tempList = new ArrayList<>();

		for (Map.Entry<Double, Double> entry : signalOne.getValues().entrySet()) {
			tempList.add(entry.getValue());
		}

		int i = 0;
		for (Map.Entry<Double, Double> entry : signalTwo.getValues().entrySet()) {
			entry.setValue(entry.getValue() + tempList.get(i));
			i++;
		}

		Signal resultSignal = new Signal();
		resultSignal.setName(signalOne.getName() + "Plus" + signalTwo.getName());
		resultSignal.setSampling(signalTwo.getSampling());
		resultSignal.setValues(signalTwo.getValues());
		resultSignal.setFulfillment(signalTwo.getFulfillment());
		resultSignal.setPeriod(signalTwo.getPeriod());
		resultSignal.setStartingTime(signalTwo.getStartingTime());
		resultSignal.setDurationTime(signalTwo.getDurationTime());

		resultSignal.setAmplitude(calculateAmplitude(resultSignal));

		return resultSignal;
	}

	public Signal subtractSignals(Signal signalOne, Signal signalTwo) throws WrongSamplingException {
		if (!signalOne.getSampling().equals(signalTwo.getSampling())) {
			throw new WrongSamplingException("Sampling of two signals does't match");
		}

		List<Double> tempList = new ArrayList<Double>();

		for (Map.Entry<Double, Double> entry : signalOne.getValues().entrySet()) {
			tempList.add(entry.getValue());
		}

		int i = 0;
		for (Map.Entry<Double, Double> entry : signalTwo.getValues().entrySet()) {
			entry.setValue(entry.getValue() - tempList.get(i));
			i++;
		}

		Signal resultSignal = new Signal();
		resultSignal.setName(signalOne.getName() + "Subtract" + signalTwo.getName());
		resultSignal.setSampling(signalTwo.getSampling());
		resultSignal.setValues(signalTwo.getValues());
		resultSignal.setFulfillment(signalTwo.getFulfillment());
		resultSignal.setPeriod(signalTwo.getPeriod());
		resultSignal.setStartingTime(signalTwo.getStartingTime());
		resultSignal.setDurationTime(signalTwo.getDurationTime());

		resultSignal.setAmplitude(calculateAmplitude(resultSignal));

		return resultSignal;
	}

	public Signal multiplySignals(Signal signalOne, Signal signalTwo) throws WrongSamplingException {
		if (!signalOne.getSampling().equals(signalTwo.getSampling())) {
			throw new WrongSamplingException("Sampling of two signals does't match");
		}

		List<Double> tempList = new ArrayList<Double>();

		for (Map.Entry<Double, Double> entry : signalOne.getValues().entrySet()) {
			tempList.add(entry.getValue());
		}

		int i = 0;
		for (Map.Entry<Double, Double> entry : signalTwo.getValues().entrySet()) {
			entry.setValue(entry.getValue() * tempList.get(i));
			i++;
		}

		Signal resultSignal = new Signal();
		resultSignal.setName(signalOne.getName() + "Multiply" + signalTwo.getName());
		resultSignal.setSampling(signalTwo.getSampling());
		resultSignal.setValues(signalTwo.getValues());
		resultSignal.setFulfillment(signalTwo.getFulfillment());
		resultSignal.setPeriod(signalTwo.getPeriod());
		resultSignal.setStartingTime(signalTwo.getStartingTime());
		resultSignal.setDurationTime(signalTwo.getDurationTime());

		resultSignal.setAmplitude(calculateAmplitude(resultSignal));

		return resultSignal;
	}

	public Signal divideSignals(Signal signalOne, Signal signalTwo) throws WrongSamplingException, ArithmeticException {
		if (!signalOne.getSampling().equals(signalTwo.getSampling())) {
			throw new WrongSamplingException("Sampling of two signals does't match");
		}

		List<Double> tempList = new ArrayList<Double>();

		for (Map.Entry<Double, Double> entry : signalOne.getValues().entrySet()) {
			tempList.add(entry.getValue());
		}

		int i = 0;
		for (Map.Entry<Double, Double> entry : signalTwo.getValues().entrySet()) {
			if (tempList.get(i) == 0) {
				throw new ArithmeticException();
			}
			entry.setValue(entry.getValue() / tempList.get(i));
			i++;

		}

		Signal resultSignal = new Signal();
		resultSignal.setName(signalOne.getName() + "Divide" + signalTwo.getName());
		resultSignal.setSampling(signalTwo.getSampling());
		resultSignal.setValues(signalTwo.getValues());
		resultSignal.setFulfillment(signalTwo.getFulfillment());
		resultSignal.setPeriod(signalTwo.getPeriod());
		resultSignal.setStartingTime(signalTwo.getStartingTime());
		resultSignal.setDurationTime(signalTwo.getDurationTime());

		resultSignal.setAmplitude(calculateAmplitude(resultSignal));

		return resultSignal;
	}

	private Double calculateAmplitude(Signal signal) {
		Double max = Collections.max(signal.getValues().values());
		Double min = Math.abs(Collections.min(signal.getValues().values()));

		if (max > min) {
			max *= 1000;
			max = (double) Math.round(max);
			max /= 1000;
			return max;
		} else {
			min *= 1000;
			min = (double) Math.round(min);
			min /= 1000;
			return min;
		}

	}
}
