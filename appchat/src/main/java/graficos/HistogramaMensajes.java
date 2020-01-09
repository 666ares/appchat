package graficos;

import java.util.Arrays;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler.LegendPosition;

public class HistogramaMensajes {
	
	CategoryChart chart;
	
	public HistogramaMensajes() {
		chart = new CategoryChartBuilder().width(1000).height(600).title("Numero de mensajes")
										  .xAxisTitle("Mes").yAxisTitle("Numero Mensajes").build();
		
		chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
	    chart.getStyler().setHasAnnotations(true);
	}
	
	public void addElemento(Integer[] valores) {
		chart.addSeries("Mensajes", Arrays.asList(new String[] { "Enero", "Febrero", "Marzo",
						"Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre",
						"Noviembre", "Diciembre"}), Arrays.asList(valores));
	}
	
	public CategoryChart getChart() {
		return this.chart;
	}
	
}
