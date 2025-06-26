package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

import de.htwg.monopoly.util.{Observable, Observer}

class ObservableSpec extends AnyWordSpec {
  
    "Observable" should {
        "notify its observers when updated" in {
            class TestObserver extends Observer {
            var updated: Boolean = false
            def update: Unit = updated = true
            }
            
            val observable = new Observable
            val observer1 = new TestObserver
            val observer2 = new TestObserver
            
            observable.add(observer1)
            observable.add(observer2)
            
            observable.notifyObservers
            
            observer1.updated should be (true)
            observer2.updated should be (true)
        }
    
        "not notify removed observers" in {
            class TestObserver extends Observer {
            var updated: Boolean = false
            def update: Unit = updated = true
            }
            
            val observable = new Observable
            val observer1 = new TestObserver
            val observer2 = new TestObserver
            
            observable.add(observer1)
            observable.add(observer2)
            
            observable.remove(observer1)
            
            observable.notifyObservers
            
            observer1.updated should be (false)
            observer2.updated should be (true)
        }
    
        "allow adding and removing observers" in {
            class TestObserver extends Observer {
            var updated: Boolean = false
            def update: Unit = updated = true
            }
            
            val observable = new Observable
            val observer1 = new TestObserver
            val observer2 = new TestObserver
            
            observable.add(observer1)
            observable.add(observer2)
            
            observable.notifyObservers
            
            observer1.updated should be (true)
            observer2.updated should be (true)
            
            observable.remove(observer1)
            
            observable.notifyObservers
            
            observer1.updated should be (true)
            observer2.updated should be (true)
            
            observable.add(observer1)
            
            observable.notifyObservers
            
            observer1.updated should be (true)
            observer2.updated should be (true)
        }
    }
}