package uk.co.amicum.yab
 
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.control._
import scalafx.scene.layout._
import scalafx.scene.web.WebView

case class BrowserTab(var url: String = "http://www.gnu.org/") extends Tab {

  text = "Thingy"

  val locationField = new TextField {
    text = url
    hgrow = Priority.Always
  }

  def load(url: String) {
    import scala.io.Source
    println(s"reading $url")
    val html = Source.fromURL(url).mkString
    println(html)
    engine.loadContent(html)
  }


  lazy val forwardButton = new Button {
    text = "→"
    onAction = handle {
      engine.getHistory.go(1)
    }
    disable() = true
    engine.getHistory.currentIndexProperty.onChange { (_,_,n) =>
      disable() = n == engine.getHistory.getEntries.size() - 1
    }
  }

  val goButton = new Button {
    text = "Go"
    defaultButton = true
  }

  lazy val backButton = new Button {
    text = "←"
    onAction = { _ : ActionEvent =>
      engine.getHistory.go(-1)
    }
    disable <== engine.getHistory.currentIndexProperty() === 0
  }


  val webView = new WebView {
    // Update location field is page is redirected
    engine.location.onChange{ (_, _, newValue) =>
      locationField.text = newValue
    }
    // Load default page
    load(url)
    text <== engine.title
  }

  def engine = webView.engine

  def validUrl(url: String) = if (url.startsWith("http://")) url else "http://" + locationField.text()
  def loadAction(ae: ActionEvent) = {
    load(validUrl(locationField.text()))
  }
  locationField.onAction = loadAction _ 

  val toolbar = new HBox {
    spacing = 5
    margin = Insets(top = 0, right = 0, bottom = 5, left = 0)
    children = List(
      backButton,
      forwardButton,
      locationField,
      goButton)
  }
  content = new BorderPane {
    padding = Insets(5)
    top = toolbar
    center = webView
  }
}
