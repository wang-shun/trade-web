// Create a new directed graph
var g = new dagreD3.graphlib.Graph().setGraph({});
 g.setGraph({
    nodesep: 70,
    ranksep: 50,
    rankdir: "LR",
    marginx: 20,
    marginy: 20
  });

// States and transitions from RFC 793
var states = {

  "首次跟进": {
    description: "2016年8月27日，张晓敏首次跟进，已完成" ,
    style: "fill: #f6eac4",
     shape: "circle"
  },

  "无效案件审批": {
    description: "2016年8月29日，张晓敏无效案件审批，已完成" ,
    style: "fill: #f6eac4",
    shape: "ellipse"
  },

  "签约": {
    description: "2016年8月28日，张晓敏成功签约" ,
    style: "fill: #f6eac4",shape: "diamond"
  },

  "贷款需求选择": {
    description: "2016年8月30日，张晓敏填写贷款需求选择" ,
    style: "fill: #c4ecf6",
  },

  "填写交易计划": {
    description: "2016年8月29日，张晓敏填写填写交易计划" ,
    style: "fill: #f6eac4"
  },


  "查限购": {
    description: "2016年8月31日，张晓敏查限购" ,
    style: "fill: #c4ecf6"
  },

  "核价": {
    description: "2016年9月1日，核价进行中" ,
    style: "fill: #c4ecf6"
  },

  "上家贷款结清": {
    description: "上家贷款结清进行中" ,
    style: "fill: #c4ecf6"
  },

  "审税": {
    description: "2016年9月1日审税" ,
    style: "fill: #c4ecf6"
  },

  "过户": {
    description: "过户未完成" ,
    style: "fill: #ccc"
  },

  "过户审批": {
    description: "过户审批未完成" ,
    style: "fill: #ccc"
  },

  "过户信息修改": {
    description: "过户信息修改未完成" ,
    style: "fill: #ccc"
  },

  "领证": {
    description: "领证未完成" ,
    style: "fill: #ccc"
  },

  "发起报告类评估": {
    description: "发起报告类评估未完成" ,
    style: "fill: #ccc"
  },

  "放款": {
    description: "放款未完成" ,
    style: "fill: #ccc"
  },

  "结案归档": {
    description: "结案归档未完成" ,
    style: "fill: #ccc"
  },

  "结案审核": {
    description: "结案审核未完成" ,
    style: "fill: #ccc"
  },

  "主管审批": {
    description: "主管审批未完成" ,
    style: "fill: #ccc"
  },



/*  ESTAB: {
    description: "我是橡皮 " ,
    style: "fill: #c4ecf6"
  }*/
};

// Add states to the graph, set labels, and style
Object.keys(states).forEach(function(state) {
  var value = states[state];
  value.label = state;
  value.rx = value.ry = 5;
  g.setNode(state, value);
});

// Set up the edges
/*g.setEdge("首次跟进",     "主管审批",     { label: "open" });
g.setEdge("主管审批",     "结案审核",     {
  style: "stroke: #f66; stroke-width: 3px; stroke-dasharray: 5, 5;",
  arrowheadStyle: "fill: #f66",
  label: "Arrowhead class",
  lineInterpolate: 'basis',
});*/
g.setEdge("首次跟进", "无效案件审批", { label: "" });
g.setEdge("首次跟进", "签约", { label: "" });
g.setEdge("首次跟进", "贷款需求选择", { label: "" });


g.setEdge("签约", "填写交易计划", { label: "" });
g.setEdge("填写交易计划", "查限购", { label: "" });
g.setEdge("填写交易计划", "核价", { label: "" });
g.setEdge("核价", "审税", { label: "" });
g.setEdge("填写交易计划", "上家贷款结清", { label: "" });
g.setEdge("查限购", "过户", { label: "" });
g.setEdge("审税", "过户", { label: "" });
g.setEdge("上家贷款结清", "过户", { label: "" });
g.setEdge("贷款需求选择", "过户", { label: "" });
g.setEdge("过户", "过户审批", { label: "" });
g.setEdge("过户审批", "过户信息修改", { label: "" });
g.setEdge("过户审批", "过户信息修改", { label: "" });
g.setEdge("过户信息修改", "领证", { label: "" });
g.setEdge("过户审批", "发起报告类评估", { label: "" });
g.setEdge("发起报告类评估", "领证", { label: "" });
g.setEdge("领证", "过户审批", { label: "" });
g.setEdge("领证", "结案归档", { label: "" });
g.setEdge("领证", "放款", { label: "" });
g.setEdge("放款", "结案归档", { label: "" });
g.setEdge("结案归档", "结案审核", { label: "" });
g.setEdge("结案归档", "主管审批", { label: "" });
g.setEdge("结案审核", "主管审批", { label: "" });










// Create the renderer
var render = new dagreD3.render();

// Set up an SVG group so that we can translate the final graph.
var svg = d3.select("svg"),
    inner = svg.append("g");

// Set up zoom support
var zoom = d3.behavior.zoom().on("zoom", function() {
    inner.attr("transform", "translate(" + d3.event.translate + ")" +
                                "scale(" + d3.event.scale + ")");
  });
svg.call(zoom);

// Simple function to style the tooltip for the given node.
var styleTooltip = function(name, description) {
  return "<p class='name'>" + name + "</p><p class='description'>" + description + "</p>";
};

// Run the renderer. This is what draws the final graph.
render(inner, g);

inner.selectAll("g.node")
  .attr("title", function(v) { return styleTooltip(v, g.node(v).description) })
  .each(function(v) { $(this).tipsy({ gravity: "w", opacity: 1, html: true }); });

// Center the graph
var initialScale = 0.75;
/*zoom
  .translate([(svg.attr("width") - g.graph().width * initialScale) / 2, 20])
  .scale(initialScale)
  .event(svg);
svg.attr('height', g.graph().height * initialScale + 100);*/
zoom
  .translate([10, 50])
  .scale(initialScale)
  .event(svg);
svg.attr('height', g.graph().height * initialScale + 170);