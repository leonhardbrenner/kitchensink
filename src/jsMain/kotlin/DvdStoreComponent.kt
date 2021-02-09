import DvdRentalWindow.DisplayComponent.ComponentStyles.inline
import DvdRentalWindow.DisplayComponent.ComponentStyles.listDiv
import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.list.*
import com.ccfraser.muirwik.components.menu.mMenuItem
import generated.model.DvdRental
import generated.model.DvdRentalDto
import kotlinext.js.jsObject
import react.*
import react.dom.*
import kotlinx.coroutines.*
import kotlinx.css.*
import styled.StyleSheet
import styled.css
import styled.styledDiv

private val scope = MainScope()

fun RBuilder.dvdStore() = child(DvdRentalWindow.Component) {}

object DvdRentalWindow {

    val Component = functionalComponent<RProps> { _ ->
        val (age, setAge) = useState<Any>(DvdRentalDto.Actor.path)

        val inputProps: RProps = jsObject { }
        inputProps.asDynamic().name = "age"
        inputProps.asDynamic().id = "age-simple"
        mSelect(age, name = "age", onChange = { event, _ -> setAge(event.targetValue) }) {
            attrs.inputProps = inputProps
            mMenuItem("Actor", value = DvdRentalDto.Actor.path)
            mMenuItem("Address", value = DvdRentalDto.Address.path)
            mMenuItem("Category", value = DvdRentalDto.Category.path)
            mMenuItem("City", value = DvdRentalDto.City.path)
            mMenuItem("Country", value = DvdRentalDto.Country.path)
            mMenuItem("Film", value = DvdRentalDto.Film.path)
            mMenuItem("FilmActor", value = DvdRentalDto.FilmActor.path)
            mMenuItem("Inventory", value = DvdRentalDto.Inventory.path)
            mMenuItem("Language", value = DvdRentalDto.Language.path)
            mMenuItem("Payment", value = DvdRentalDto.Payment.path)
            mMenuItem("Rental", value = DvdRentalDto.Rental.path)
            mMenuItem("Staff", value = DvdRentalDto.Staff.path)
            mMenuItem("Store", value = DvdRentalDto.Store.path)
        }
        when (age) {
            DvdRentalDto.Actor.path -> actor {}
            DvdRentalDto.Address.path -> address {}
            DvdRentalDto.Category.path -> category {}
            DvdRentalDto.City.path ->  city{}
            DvdRentalDto.Country.path ->  country{}
            DvdRentalDto.Customer.path ->  customer{}
            DvdRentalDto.Film.path ->  film{}
            DvdRentalDto.FilmActor.path -> filmActor{}
            DvdRentalDto.FilmCategory.path -> filmCategory{}
            DvdRentalDto.Inventory.path -> inventory {}
            DvdRentalDto.Language.path -> language {}
            DvdRentalDto.Payment.path -> payment {}
            DvdRentalDto.Rental.path -> rental {}
            DvdRentalDto.Staff.path -> staff {}
            DvdRentalDto.Store.path -> store {}
        }

    }

    interface Props : RProps
    interface State<T> : RState {
        var items: List<Pair<String, T>>
        var currentSeed: String
    }

    private abstract class DisplayComponent<T> (props: Props) : RComponent<Props, State<T>>() {
        private object ComponentStyles : StyleSheet("ComponentStyles", isStatic = true) {
            val listDiv by css {
                display = Display.inlineFlex
                padding(1.spacingUnits)
            }

            val inline by css {
                display = Display.inlineBlock
            }
        }

        override fun State<T>.init() {
            items = listOf()
            scope.launch {
                val seeds: List<T> = get()
                setState {
                    items = seeds.map { it.label() to it }
                }
            }
        }

        override fun RBuilder.render() {
            // For building things that we don't want to render now (e.g. the component will render it later), we need another builder
            val builder2 = RBuilder()
            themeContext.Consumer { theme ->
                val themeStyles = object : StyleSheet("ComponentStyles", isStatic = true) {
                    val list by css {
                        width = 320.px
                        backgroundColor = Color(theme.palette.background.paper)
                    }
                }
                styledDiv {
                    css(listDiv)
                    mList {
                        css(themeStyles.list)
                        state.items.forEach { (name, callback) ->
                            mListItem(alignItems = MListItemAlignItems.flexStart, button = true, onClick = {
                                setState {
                                    currentSeed = callback.transform()
                                }
                            }) {
                                mListItemText(builder2.span { +name }, builder2.span {
                                    mTypography(name + " again", component = "span", variant = MTypographyVariant.body2) { css(inline) }
                                })
                            }
                        }
                    }
                    mContainer {
                        mTypography(state.currentSeed, component = "span", variant = MTypographyVariant.body2) { css(inline) }
                    }
                }
            }
        }
        abstract suspend fun get(): List<T>
        abstract fun T.label(): String
        abstract fun T.transform(): String

    }

    private class Actor(props: Props): DisplayComponent<DvdRental.Actor>(props) {
        override suspend fun get(): List<DvdRental.Actor> = DvdRentalApi.getActor()
        override fun DvdRental.Actor.label() = "$firstName $lastName"
        override fun DvdRental.Actor.transform() = actorId.toString()
    }
    fun RBuilder.actor(handler: Props.() -> Unit) = child(Actor::class) { attrs { handler() } }

    private class Address(props: Props): DisplayComponent<DvdRental.Address>(props) {
        override suspend fun get(): List<DvdRental.Address> = DvdRentalApi.getAddress()
        override fun DvdRental.Address.label() = "$address $phone"
        override fun DvdRental.Address.transform() = addressId.toString()
    }
    fun RBuilder.address(handler: Props.() -> Unit) = child(Address::class) { attrs { handler() } }

    private class Category(props: Props): DisplayComponent<DvdRental.Category>(props) {
        override suspend fun get(): List<DvdRental.Category> = DvdRentalApi.getCategory()
        override fun DvdRental.Category.label() = "$name"
        override fun DvdRental.Category.transform() = categoryId.toString()
    }
    fun RBuilder.category(handler: Props.() -> Unit) = child(Category::class) { attrs { handler() } }

    private class City(props: Props): DisplayComponent<DvdRental.City>(props) {
        override suspend fun get(): List<DvdRental.City> = DvdRentalApi.getCity()
        override fun DvdRental.City.label() = "$city"
        override fun DvdRental.City.transform() = cityId.toString()
    }
    fun RBuilder.city(handler: Props.() -> Unit) = child(City::class) { attrs { handler() } }

    private class Country(props: Props): DisplayComponent<DvdRental.Country>(props) {
        override suspend fun get(): List<DvdRental.Country> = DvdRentalApi.getCountry()
        override fun DvdRental.Country.label() = "$country"
        override fun DvdRental.Country.transform() = countryId.toString()
    }
    fun RBuilder.country(handler: Props.() -> Unit) = child(Country::class) { attrs { handler() } }

    private class Customer(props: Props): DisplayComponent<DvdRental.Customer>(props) {
        override suspend fun get(): List<DvdRental.Customer> = DvdRentalApi.getCustomer()
        override fun DvdRental.Customer.label() = "$firstName $lastName"
        override fun DvdRental.Customer.transform() = customerId.toString()
    }
    fun RBuilder.customer(handler: Props.() -> Unit) = child(Customer::class) { attrs { handler() } }

    private class Film(props: Props): DisplayComponent<DvdRental.Film>(props) {
        override suspend fun get(): List<DvdRental.Film> = DvdRentalApi.getFilm()
        override fun DvdRental.Film.label() = "$title"
        override fun DvdRental.Film.transform() = filmId.toString()
    }
    fun RBuilder.film(handler: Props.() -> Unit) = child(Film::class) { attrs { handler() } }

    private class FilmActor(props: Props): DisplayComponent<DvdRental.FilmActor>(props) {
        override suspend fun get(): List<DvdRental.FilmActor> = DvdRentalApi.getFilmActor()
        override fun DvdRental.FilmActor.label() = "$filmId"
        override fun DvdRental.FilmActor.transform() = "$filmId $actorId"
    }
    fun RBuilder.filmActor(handler: Props.() -> Unit) = child(FilmActor::class) { attrs { handler() } }

    private class FilmCategory(props: Props): DisplayComponent<DvdRental.FilmCategory>(props) {
        override suspend fun get(): List<DvdRental.FilmCategory> = DvdRentalApi.getFilmCategory()
        override fun DvdRental.FilmCategory.label() = "$filmId"
        override fun DvdRental.FilmCategory.transform() = filmId.toString()
    }
    fun RBuilder.filmCategory(handler: Props.() -> Unit) = child(FilmCategory::class) { attrs { handler() } }

    private class Inventory(props: Props): DisplayComponent<DvdRental.Inventory>(props) {
        override suspend fun get(): List<DvdRental.Inventory> = DvdRentalApi.getInventory()
        override fun DvdRental.Inventory.label() = "$filmId $storeId"
        override fun DvdRental.Inventory.transform() = inventoryId.toString()
    }
    fun RBuilder.inventory(handler: Props.() -> Unit) = child(Inventory::class) { attrs { handler() } }

    private class Language(props: Props): DisplayComponent<DvdRental.Language>(props) {
        override suspend fun get(): List<DvdRental.Language> = DvdRentalApi.getLanguage()
        override fun DvdRental.Language.label() = "$name"
        override fun DvdRental.Language.transform() = languageId.toString()
    }
    fun RBuilder.language(handler: Props.() -> Unit) = child(Language::class) { attrs { handler() } }

    private class Payment(props: Props): DisplayComponent<DvdRental.Payment>(props) {
        override suspend fun get(): List<DvdRental.Payment> = DvdRentalApi.getPayment()
        override fun DvdRental.Payment.label() = "$customerId $staffId $rentalId $paymentDate"
        override fun DvdRental.Payment.transform() = paymentId.toString()
    }
    fun RBuilder.payment(handler: Props.() -> Unit) = child(Payment::class) { attrs { handler() } }

    private class Rental(props: Props): DisplayComponent<DvdRental.Rental>(props) {
        override suspend fun get(): List<DvdRental.Rental> = DvdRentalApi.getRental()
        override fun DvdRental.Rental.label() = "$rentalId"
        override fun DvdRental.Rental.transform() = rentalId.toString()
    }
    fun RBuilder.rental(handler: Props.() -> Unit) = child(Rental::class) { attrs { handler() } }

    private class Staff(props: Props): DisplayComponent<DvdRental.Staff>(props) {
        override suspend fun get(): List<DvdRental.Staff> = DvdRentalApi.getStaff()
        override fun DvdRental.Staff.label() = "$staffId"
        override fun DvdRental.Staff.transform() = staffId.toString()
    }
    fun RBuilder.staff(handler: Props.() -> Unit) = child(Staff::class) { attrs { handler() } }

    private class Store(props: Props): DisplayComponent<DvdRental.Store>(props) {
        override suspend fun get(): List<DvdRental.Store> = DvdRentalApi.getStore()
        override fun DvdRental.Store.label() = "$storeId"
        override fun DvdRental.Store.transform() = storeId.toString()
    }
    fun RBuilder.store(handler: Props.() -> Unit) = child(Store::class) { attrs { handler() } }

}
