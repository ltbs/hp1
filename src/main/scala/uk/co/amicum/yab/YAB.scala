
package uk.co.amicum.yab
 
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.control._
import scalafx.scene.layout._
 
object YAB extends JFXApp {

  def app = this

  stage = new JFXApp.PrimaryStage {
    title = "YAB"
    width = 1020
    height = 700
    scene = new Scene {      
      root = {
        // Default URL to load at first

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

         new BorderPane {
          padding = Insets(5)
           top = menubar
           
           center = new TabPane {
             tabs = Seq(BrowserTab("http://www.google.co.uk"), BrowserTab())
           }
         }
      }
    }
  }
}
