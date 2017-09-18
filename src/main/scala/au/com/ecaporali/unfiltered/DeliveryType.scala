package au.com.ecaporali.unfiltered

object DeliveryType extends Enumeration {
  type DeliveryType = Value

  val DELIVER: Value = Value("Deliver")
  val PICK_UP: Value = Value("Pick Up")
}
