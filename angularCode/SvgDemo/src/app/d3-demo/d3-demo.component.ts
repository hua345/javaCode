import { Component, OnInit, ViewChild } from '@angular/core';
import { LineChart } from './line.chart';

@Component({
  selector: 'app-d3-demo',
  templateUrl: './d3-demo.component.html',
  styleUrls: ['./d3-demo.component.css']
})
export class D3DemoComponent implements OnInit {

  chart: LineChart;
  @ViewChild('target') target; // 获得子组件的引用
  constructor() { }

  ngOnInit() {
    this.chart = new LineChart(this.target.nativeElement);
    this.chart.render();
  }
}
