package graficos;

import org.knowm.xchart.PieChartBuilder;

import java.awt.Color;

import org.knowm.xchart.*;

public class PieChartGrupos {

	PieChart chart;
	
	public PieChartGrupos() {
		chart = new PieChartBuilder().width(400).height(400).title("Estadisticas Grupos").build();
		Color[] sliceColors = new Color[] { new Color(224, 68, 14), 
											new Color(230, 105, 62), 
											new Color(236, 143, 110), 
											new Color(243, 180, 159), 
											new Color(246, 199, 182)};
		chart.getStyler().setSeriesColors(sliceColors);
		
	}
	
	public void setSerie(String nombre, int valor) {
		chart.addSeries(nombre, valor);
	}
	
	public PieChart getChart() {
		return chart;
	}
	
}
