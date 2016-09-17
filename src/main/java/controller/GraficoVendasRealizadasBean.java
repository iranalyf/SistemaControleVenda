package controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import repository.VendaDAO;

@Named
@RequestScoped
public class GraficoVendasRealizadasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static DateFormat dt = new SimpleDateFormat("dd/MM");

	private LineChartModel lineModel1;

	@Inject
	private VendaDAO repository;

	@PostConstruct
	public void init() {
		createLineModels();
	}

	public LineChartModel getLineModel1() {
		return lineModel1;
	}

	private void createLineModels() {
		lineModel1 = initLinearModel();
		lineModel1.setTitle("Relação de Vendas");
		lineModel1.setLegendPosition("e");
		lineModel1.setShowPointLabels(true);
		lineModel1.getAxes().put(AxisType.X, new CategoryAxis("Datas das Vendas  "));
		Axis yAxis = lineModel1.getAxis(AxisType.Y);
		yAxis.setLabel("Valor(R$)");

		yAxis.setMin(0);
		yAxis.setMax(10000);

	}

	private LineChartModel initLinearModel() {
		LineChartModel model = new LineChartModel();

		Map<Date, BigDecimal> valoresPorData = repository.valoresTotaisPorData(15, null);

		LineChartSeries valor = new LineChartSeries();

		for (Date data : valoresPorData.keySet()) {
			valor.set(dt.format(data), valoresPorData.get(data));
		}

		valor.setLabel("Todas as Vendas");
		model.addSeries(valor);

		return model;
	}
}