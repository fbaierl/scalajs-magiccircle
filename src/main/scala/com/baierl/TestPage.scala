package com.baierl

import org.scalajs.dom.{EventTarget, html}

import scala.language.implicitConversions
import rx._
import org.singlespaced.d3js.Ops._
import org.singlespaced.d3js.d3
import org.singlespaced.d3js.d3.Primitive

import scala.scalajs.js
import js.JSConverters._
import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("MagicCircle")
object TestPage
{

  private val circleData = Var[List[Circle]](List.empty)
  private val height = 500
  private val width = 500
  private val circleCount = 5

  case class Circle (id: Int, var x: Int){
    def y : Int = id * 100 + 40
    def radius = 10
  }
  private def randomX = scala.util.Random.nextInt(300) + 100

  @JSExportTopLevel("main")
  def main(target: html.Div) : Unit = {

    def moveCircles() : Unit = {
      circleData.now.foreach(c => c.x = randomX)
      circleData.recalc()
    }

    // create main svg
    d3.select("#content")
        .append("svg")
          .attr("id", "graph")
          .attr("height", height)
          .attr("width", width)
          .attr("xmlns", "http://www.w3.org/2000/svg")
          .on("click", { _: EventTarget => moveCircles() })
          .append("rect")
            .attr("height", height)
            .attr("width", width)
            .attr("fill", "black")

    // create random circles
    0 until circleCount foreach {
      i => circleData() = Circle(i, randomX) :: circleData.now
    }

    // start timer to move circles every 3 seconds
    js.timers.setInterval(3000) {
      moveCircles()
    }
  }

  val tween : js.Function3[Circle, Double, String, js.Function1[Double, Primitive]] =
    (c: Circle, _: Double, s: String) => {
      val start = s.toDouble
      val goal = c.x
      val distance = Math.abs(start - goal)
      def interpolation :  Double => Primitive = t => {
        def newX = start + (if(goal < start) -1 else 1) * (t * distance)
        d3.select(s"#circle${c.id}").attr("cy", c.y + 20 * Math.sin(newX / 15))
        newX
      }
      interpolation
    } : Double => Primitive

  circleData.triggerLater {
    val selection = d3.select("#graph")
                      .selectAll(".circle")
                      .data(circleData.now.toJSArray, (c: Circle) => c.id.toString)

    selection.enter()
      .append("circle")
        .attr("class", "circle")
        .attr("id", (c: Circle) => "circle" + c.id)
        .attr("r", (c: Circle) => c.radius)
        .attr("cx", (c: Circle) => c.x)
        .attr("cy", (c: Circle) => c.y)
        .attr("fill", "white")

    selection.exit().remove()

    selection.transition()
      .duration(1000)
      .attrTween("cx", tween)
  }
}