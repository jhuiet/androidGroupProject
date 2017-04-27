package jolliffe.a415.csc.groupproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Graph extends AppCompatActivity implements View.OnClickListener {
    private Button crunchBtn;
    private Button pushupBtn;
    private Button squatBtn;
    private Button benchBtn;
    //private int selector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        // generate Dates
        /*Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d4 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d5 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d6 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d7 = calendar.getTime();*/
        benchBtn = (Button) findViewById(R.id.benchpressbutton);
        squatBtn = (Button) findViewById(R.id.squatsbutton);
        pushupBtn = (Button) findViewById(R.id.pushupbutton);
        crunchBtn = (Button) findViewById(R.id.crunchesbutton);


        benchBtn.setOnClickListener(this);
        squatBtn.setOnClickListener(this);
        pushupBtn.setOnClickListener(this);
        crunchBtn.setOnClickListener(this);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> rep = new LineGraphSeries<>(new DataPoint[] {

                new DataPoint(1, 1),
                new DataPoint(2, 4),
                new DataPoint(3, 3),
                new DataPoint(4, 5),
                new DataPoint(5, 6),
                new DataPoint(6, 4),
                new DataPoint(7, 6),
                new DataPoint(8, 1),
                new DataPoint(9, 4),
                new DataPoint(10, 3),
                new DataPoint(11, 5),
                new DataPoint(12, 6),
                new DataPoint(13, 4),
                new DataPoint(14, 6),
                new DataPoint(15, 1),
                new DataPoint(16, 4),
                new DataPoint(17, 3),
                new DataPoint(18, 5),
                new DataPoint(19, 6),
                new DataPoint(20, 4)/*,
                new DataPoint(21, 6),
                new DataPoint(22, 1),
                new DataPoint(23, 4),
                new DataPoint(24, 3),
                new DataPoint(25, 5),
                new DataPoint(26, 6),
                new DataPoint(27, 4),
                new DataPoint(28, 6),
                new DataPoint(29, 1),
                new DataPoint(30, 4),
                new DataPoint(31, 3)*/
        });
        //if (selector == 0) {
            graph.addSeries(rep);
        //}
        rep.setColor(Color.GREEN);

        LineGraphSeries<DataPoint> set = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 3),
                new DataPoint(2, 5),
                new DataPoint(3, 4),
                new DataPoint(4, 3),
                new DataPoint(5, 6),
                new DataPoint(6, 4),
                new DataPoint(7, 3),
                new DataPoint(8, 5),
                new DataPoint(9, 2),
                new DataPoint(10, 4),
                new DataPoint(11, 1),
                new DataPoint(12, 3),
                new DataPoint(13, 5),
                new DataPoint(14, 2),
                new DataPoint(15, 3),
                new DataPoint(16, 2),
                new DataPoint(17, 5),
                new DataPoint(18, 4),
                new DataPoint(19, 3),
                new DataPoint(20, 5)/*,
                new DataPoint(21, 6),
                new DataPoint(22, 1),
                new DataPoint(23, 4),
                new DataPoint(24, 3),
                new DataPoint(25, 5),
                new DataPoint(26, 6),
                new DataPoint(27, 4),
                new DataPoint(28, 6),
                new DataPoint(29, 1),
                new DataPoint(30, 4),
                new DataPoint(31, 3)*/
        });
        //if (selector == 1) {
            graph.addSeries(set);
        //}
        set.setColor(Color.BLUE);

        LineGraphSeries<DataPoint> weight = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 40),
                new DataPoint(2, 47),
                new DataPoint(3, 45),
                new DataPoint(4, 40),
                new DataPoint(5, 46),
                new DataPoint(6, 50),
                new DataPoint(7, 45),
                new DataPoint(8, 44),
                new DataPoint(9, 48),
                new DataPoint(10, 52),
                new DataPoint(11, 50),
                new DataPoint(12, 49),
                new DataPoint(13, 55),
                new DataPoint(14, 42),
                new DataPoint(15, 47),
                new DataPoint(16, 51),
                new DataPoint(17, 50),
                new DataPoint(18, 51),
                new DataPoint(19, 49),
                new DataPoint(20, 54)/*,
                new DataPoint(21, 5),
                new DataPoint(22, 1),
                new DataPoint(23, 4),
                new DataPoint(24, 3),
                new DataPoint(25, 5),
                new DataPoint(26, 6),
                new DataPoint(27, 4),
                new DataPoint(28, 6),
                new DataPoint(29, 1),
                new DataPoint(30, 4),
                new DataPoint(31, 3)*/
        });
        //if (selector == 2) {
            graph.addSeries(weight);
        //}
        weight.setColor(Color.RED);


        // set date label formatter
        //graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplication()));
        //graph.getGridLabelRenderer().setNumHorizontalLabels(7); // only 4 because of the space

// set manual x bounds to have nice steps
        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(20);
        /*graph.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.squatsbutton: {
                //selector = 0;
                //startActivity(new Intent(MainActivity.this, RepGraph.class));
                Intent intent = new Intent(Graph.this, DisplaySquats.class);
                startActivity(intent);
                //recreate();
                break;
            }
            case R.id.pushupbutton:{
                //selector = 0;
                //startActivity(new Intent(MainActivity.this, RepGraph.class));
                Intent intent = new Intent(Graph.this, DisplayPushups.class);
                startActivity(intent);
                //recreate();
                break;
            }
            case R.id.crunchesbutton:{
                //selector = 0;
                //startActivity(new Intent(MainActivity.this, RepGraph.class));
                Intent intent = new Intent(Graph.this, DisplayCrunches.class);
                startActivity(intent);
                //recreate();
                break;
            }

            case R.id.benchpressbutton:{
                //selector = 0;
                //startActivity(new Intent(MainActivity.this, RepGraph.class));
                Intent intent = new Intent(Graph.this, DisplayBenchPress.class);
                startActivity(intent);
                //recreate();
                break;
            }
        }
    }

}
