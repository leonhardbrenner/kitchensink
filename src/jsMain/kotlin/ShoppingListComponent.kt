import react.*
import react.dom.*
import kotlinx.coroutines.*
import kotlinx.html.js.onClickFunction
import models.ShoppingListItem

private val scope = MainScope()

val ShoppingListComponent = functionalComponent<RProps> { _ ->
    val (shoppingList, setShoppingList) = useState(emptyList<ShoppingListItem>())

    useEffect(dependencies = listOf()) {
        scope.launch {
            setShoppingList(ShoppingListApi.get())
        }
    }

    ul {
        shoppingList.sortedByDescending(ShoppingListItem::priority).forEach { item ->
            li {
                key = item.toString()
                attrs.onClickFunction = {
                    scope.launch {
                        ShoppingListApi.delete(item)
                        setShoppingList(ShoppingListApi.get())
                    }
                }
                +"[${item.priority}] ${item.desc} "
            }
        }
    }

    inputComponent {
        onSubmit = { input ->
            val cartItem = ShoppingListItem(input.replace("!", ""), input.count { it == '!' })
            scope.launch {
                ShoppingListApi.addItem(cartItem)
                setShoppingList(ShoppingListApi.get())
            }
        }
    }

}

fun RBuilder.shoppingList() = child(ShoppingListComponent) {}