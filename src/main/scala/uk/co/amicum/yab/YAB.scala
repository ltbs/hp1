
package uk.co.amicum.yab
 
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.control._
import scalafx.scene.layout._
import scalafx.scene.web.WebView
 
object YAB extends JFXApp {

  def app = this

  val locationField = new TextField {
    text = defaultURL
    hgrow = Priority.Always
  }

  val goButton = new Button {
    text = "Go"
    defaultButton = true
  }

  val defaultURL = "http://www.scala-lang.org/"
  val webView = new WebView {
    // Update location field is page is redirected
    engine.location.onChange((_, _, newValue) => locationField.text = newValue)
    // Load default page
    engine.load(defaultURL)

    val backButton = new Button {
      text = "←"
      onAction = { _ : ActionEvent =>
        engine.getHistory.go(-1)
      }
      disable <== engine.getHistory.currentIndexProperty() === 0
    }

    val forwardButton = new Button {
      text = "→"
      onAction = handle {
        engine.getHistory.go(1)
      }
      disable() = true
      engine.getHistory.currentIndexProperty.onChange { (_,_,n) =>
        disable() = n == engine.getHistory.getEntries.size() - 1
      }
    }
  }


  def engine = webView.engine


  stage = new JFXApp.PrimaryStage {
    title <== engine.location
    width = 1020
    height = 700
    scene = new Scene {      
      root = {
        // Default URL to load at first
        
     
        def validUrl(url: String) = if (url.startsWith("http://")) url else "http://" + locationField.text()
     
        val loadAction = (ae: ActionEvent) =>
          webView.engine.load(validUrl(locationField.text()))
        locationField.onAction = loadAction


        val menubar = new MenuBar {
          useSystemMenuBar = true
          menus = List(
            new Menu("File") {
              items = List(
                new Menu("Submenu") {
                  items = List(
                    new MenuItem("Item 1"),
                    new MenuItem("Item 2")
                  )
                },
                new MenuItem("Quit") {
                  onAction = {_ : ActionEvent => stage.close}
                }
              )
            })
        }

        val toolbar = new HBox {
          spacing = 5
          margin = Insets(top = 0, right = 0, bottom = 5, left = 0)
          children = List(
            webView.backButton,
            webView.forwardButton,
            locationField,
            goButton)
        }

        new BorderPane {
          padding = Insets(5)
          top = new VBox {
            spacing = 5
            margin = Insets(top = 0, right = 0, bottom = 5, left = 0)
            children = List(menubar, toolbar)
          }
            
          center = webView
          bottom = new HBox{
            spacing = 5
            margin = Insets(top = 0, right = 0, bottom = 5, left = 0)            
            children = List(
              new Label{
                text = "thingy"
                webView.engine.getHistory.currentIndexProperty().onChange { (_,_,n) =>
                  text = n.toString()
                }
              }, new Label{
                text = "thingy2"
                webView.engine.getHistory.currentIndexProperty().onChange { (_,_,n) =>
                  text = webView.engine.getHistory.getEntries.size.toString()
                }
              }
            )
          }
        }
      }
    }
  }
}
