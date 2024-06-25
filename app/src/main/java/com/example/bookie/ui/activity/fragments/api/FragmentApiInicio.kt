package com.example.bookie.ui.activity.fragments.api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookie.R
import com.example.bookie.data.model.api.Book
import com.example.bookie.databinding.FragmentApiInicioBinding
import com.example.bookie.ui.activity.adapters.AdapterApiInicio
import com.example.bookie.ui.activity.viewsmodels.ApiViewModel
/**
 * Esta clase define listados predefinidos de libros sin ausencia de datos para señalar el correcto uso de la api ya que no se puede asegurar que en las búsquedas los datos se devuelvan al completo
 *@author Lorena Villar
 * @version 23/5/2024
 * @see Book
 * @see AdapterApiInicio
 * @see ApiViewModel
 * */
class FragmentApiInicio: Fragment() {
    private var _binding: FragmentApiInicioBinding? = null
    private val binding get() = _binding!!

    //Se utiliza un adapter por cada listado de libros de un género independiente
    private lateinit var listAdapterManga: AdapterApiInicio
    private lateinit var listAdapterTerror: AdapterApiInicio
    private lateinit var listAdapterPsicologico: AdapterApiInicio
    private lateinit var listAdapterCienciaFiccion: AdapterApiInicio
    private lateinit var listAdapterAventura: AdapterApiInicio
    private lateinit var listAdapterRomance: AdapterApiInicio

    private val myViewModel by activityViewModels<ApiViewModel> {
        ApiViewModel.MyViewModelFactory(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentApiInicioBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //navega al buscador
        binding.editTextSearch.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentApiInicio_to_fragmentBuscadorApi)
        }
        //función que engloba en sí las interacciones que se producen al tocar una celda del adaptador
        setupRecyclerView()

        // Función encargada de dividir el texto en frases usando el punto como delimitador para mejorar la legibilidad
        fun formatSinopsis(sinopsis: String): String {
            val frases = sinopsis.split(".")
            val stringBuilder = StringBuilder()

            for (i in frases.indices) {
                if (frases[i].isNotBlank()) {
                    stringBuilder.append(frases[i].trim())
                    stringBuilder.append(".")
                    if ((i + 1) % 2 == 0) {
                        //hace un salto de línea cada 2 puntos
                        stringBuilder.append("\n")
                    } else   if ((i + 1) % 3 == 0) {
                        //cada 3 simula un punto y aparte para mejorar la lectura
                        stringBuilder.append("\n")
                        stringBuilder.append("\n")
                    } else {
                        stringBuilder.append(" ")
                    }
                }
            }
            //retorna el resultado ya formateado
            return stringBuilder.toString().trim()
        }

        //listado predefinido de libros para crear la pantalla de inicio API
        val predefRomance = listOf(
            Book(
                "3JctEAAAQBAJ",
                "La canción de Aquiles",
                "Miller, Madeline",
                "http://books.google.com/books/content?id=3JctEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Romance",
                "Grupo Editorial Patria",
                397,
                formatSinopsis("El joven príncipe Patroclo mata por accidente a un muchacho. Repudiado por su padre, es exiliado al reino de Ftía, donde lo acoge el rey Peleo, un hombre bendecido por los dioses, inteligente, apuesto, valiente y reconocido por su piedad. Tanto que se le concedió el más alto honor, la posibilidad de engendrar un hijo con una diosa: Aquiles. Aquiles es fuerte, noble, luminoso. Patroclo no puede evitar admirar hasta el último de sus gestos; su belleza y perfección hacen que sea incapaz de contemplarlo sin una punzada de dolor. Por eso no se explica que Aquiles lo escoja como hermano de armas, un puesto de la más alta estima que lo unirá a él por lazos de sangre y lealtad, pero también de amor. Así emprenden juntos el camino de la vida, compartiendo cada instante, cada experiencia, cada aprendizaje y preparándose para el cumplimiento de una profecía: el destino de Aquiles como mejor guerrero de su generación. Especializada en cultura clásica, Madeline Miller acomete una relectura del mito de Troya, demostrando su plena actualidad y vigencia. Todos los elementos que tan familiares nos resultan y que forman una parte tan esencial de nuestra cultura tienen cabida en ella: la belleza de Helena, la fuerza de Áyax, la astucia de Ulises, la nobleza de Héctor, el sacrificio de Ifigenia, la obstinación de Agamenón... Y, sin embargo, toman una nueva dimensión, moderna y actual, en un estilo tan firme y fluido, desarrollando una trama tan inteligente y bien perfilada que resulta imposible abandonar su lectura ya desde la primera página."),
                4.5
            ),
            Book(
                "09s1DwAAQBAJ",
                "Una boda en invierno",
                "Brenda Novak",
                "http://books.google.com/books/content?id=09s1DwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Romance",
                "Harlequin",
                416,
                formatSinopsis("Kyle Houseman creía que nunca conocería a nadie a quien pudiera amar tanto como a Olivia Arnold, la mujer que se había casado con su hermanastro. No solo la había perdido a ella, sino que había tenido que sufrir todo un proceso de divorcio. Lo último que quería era volver a pasar por otro, razón por la que estaba decidido a tener mucho cuidado cuando volviera a involucrarse con una mujer. Y por eso se resistía a la atracción que sentía hacia la bella desconocida que le había alquilado su casa rural para pasar las fiestas de Navidad. Lourdes Bennett era una cantante country. Pensaba quedarse en Whiskey Creek solo durante el tiempo que necesitara para escribir las canciones de su próximo álbum, el álbum que iba a llevarla de nuevo a la cima. Sus sueños no incluían instalarse en un pueblo más pequeño incluso que aquel del que había escapado. Pero, al conocer a Kyle, comenzó a preguntarse si no sería un terrible error dejarlo atrás."),
                3.7
            ),
            Book(
                "hoH1nQEACAAJ",
                "Pídeme lo que quieras",
                "Megan Maxwell",
                "http://books.google.com/books/content?id=hoH1nQEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
                "Romance",
                "Planeta Publishing",
                467,
                formatSinopsis("Tras la muerte de su padre, el prestigioso empresario alemán Eric Zimmerman decide viajar a España para supervisar las sucursales de la empresa Müller. En la oficina central de Madrid conoce a Judith, una joven ingeniosa y simpática de la que se encapricha de inmediato. Judith sucumbe a la atracción que el alemán ejerce sobre ella y acepta formar parte de sus juegos sexuales, repletos de fantasías y erotismo. Junto a él aprenderá que todos llevamos dentro un voyeur, y que las personas se dividen en sumisas y dominantes... Pero el tiempo pasa, la relación se intensifica y Eric empieza a temer que se descubra su secreto, algo que podría marcar el principio o el fin de la relación. Pídeme lo que quieras es sin duda una novela atrevida, en la que el morbo y las fantasías sexuales están a la orden del día. No apto para menores de 18 años."),
                5.0
            ),
            Book(
                "rG6xDAAAQBAJ",
                "El amante japonés",
                "Isabel Allende",
                "http://books.google.com/books/content?id=rG6xDAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Ficción",
                "Vintage Espanol",
                354,
                formatSinopsis("A los veintidós años, sospechando que tenían el tiempo contado, Ichimei y Alma se atragantaron de amor para consumirlo entero, pero mientras más intentaban agotarlo, más imprudente era el deseo, y quien diga que todo fuego se apaga solo tarde o temprano, se equivoca: hay pasiones que son incendios hasta que las ahoga el destino de un zarpazo y aún así quedan brasas calientes listas para arder apenas se les da oxígeno."),
                4.2
            ),
            Book(
                "M-2-DwAAQBAJ",
                "Forastera (Saga Outlander 1)",
                "Diana Gabaldon",
                "http://books.google.com/books/content?id=M-2-DwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Fiction",
                "SALAMANDRA BOLSILLO",
                1058,
                formatSinopsis("Llega Forastera, la primera entrega de la saga «Outlander». Una apasionante novela que, contada con una prosa ágil y fluida, ha cimentado el éxito mundial de Diana Gabaldon y en la que se ha basado la conocida serie de televisión que lleva el mismo nombre. Comienza la historia de Claire Randall... Recién acabada la Segunda Guerra Mundial, una joven pareja se reúne por fin para pasar sus vacaciones en Escocia. Una tarde, cuando pasea sola por la pradera, Claire se acerca a un círculo de piedras antiquísimas y cae de pronto en un extraño trance. Al volver en sí se encuentra con un panorama desconcertante: el mundo moderno ha desaparecido, ahora la rodea la Escocia de 1734, con sus clanes beligerantes y supersticiosos, hombres y mujeres rudos, a veces violentos, pero con una capacidad de vivir y de amar como Claire jamás había experimentado."),
                4.6
            ),
            Book(
                "GKlCDwAAQBAJ",
                "La Sombra del Viento",
                "Carlos Ruiz Zafón",
                "http://books.google.com/books/content?id=GKlCDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Ficción",
                "Penguin",
                530,
                formatSinopsis("Desde la A hasta la Z, la serie Penguin Drop Caps recopila 26 ediciones únicas en tapa dura, cada una con una cubierta que muestra una letra del alfabeto bellamente ilustrada. Todo comienza con una letra. Enamórate de Penguin Drop Caps, una nueva serie de veintiséis ediciones coleccionables y en tapa dura, cada una con una cubierta que muestra una letra del alfabeto bellamente ilustrada. En una colaboración de diseño entre Jessica Hische y el Director de Arte de Penguin, Paul Buckley, la serie presenta arte de portada único creado por Hische, una superestrella en el mundo del diseño tipográfico e ilustración, cuyo trabajo ha aparecido en todas partes, desde Tiffany & Co. hasta la reciente película de Wes Anderson, Moonrise Kingdom, y los bestsellers de Penguin, Committed y Rules of Civility. Con diseños exclusivos que nunca antes habían aparecido en el popular blog de Hische, Penguin Drop Caps debutó con una 'A' para Orgullo y prejuicio de Jane Austen, una 'B' para Jane Eyre de Charlotte Brönte, y una 'C' para Mi Ántonia de Willa Cather. Continúa con más clásicos perennes, perfectos para regalar elegantes o para exhibir en tus propias estanterías. Z es para Zafón. Barcelona, 1945: Una ciudad se cura lentamente después de la Guerra Civil Española. Daniel, hijo de un librero anticuario que llora la pérdida de su madre, encuentra consuelo en lo que descubre en el 'cementerio de libros olvidados', un misterioso libro titulado La Sombra del Viento, escrito por Julián Carax. Pero cuando se propone encontrar otras obras del autor, hace un descubrimiento impactante: alguien ha estado destruyendo sistemáticamente cada copia de cada libro que Carax ha escrito. De hecho, Daniel puede tener el último libro existente de Carax. Pronto, la búsqueda aparentemente inocente de Daniel abre una puerta hacia uno de los secretos más oscuros de Barcelona: una historia épica de asesinato, locura y amor condenado."),
                1.0
            ),
            Book(
                "5lXhCwAAQBAJ",
                "Yo antes de ti",
                "Jojo Moyes",
                "http://books.google.com/books/content?id=5lXhCwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Ficción",
                "Penguin Random House",
                450,
                formatSinopsis("Incluye un extracto de la novela de la autora Después de ti y una guía para lectores."),
                5.0
            ),
            Book(
                "PwJWFNLKla4C",
                "Eleanor & Park",
                "Rainbow Rowell",
                "http://books.google.com/books/content?id=PwJWFNLKla4C&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Ficción para Jóvenes Adultos",
                "St. Martin's Griffin",
                336,
                formatSinopsis("#1 Best Seller del New York Times! \"Eleanor & Park me recordó no solo cómo es estar joven y enamorado de una chica, sino también cómo es estar joven y enamorado de un libro.\" - John Green, The New York Times Book Review Bono conoció a su esposa en la escuela secundaria, dice Park. Lo mismo Jerry Lee Lewis, responde Eleanor. No estoy bromeando, dice él. Deberías, dice ella, tenemos 16 años. ¿Y qué hay de Romeo y Julieta? Superficiales, confundidos, luego muertos. Te amo, dice Park. ¿Dónde estás, Eleanor responde. No estoy bromeando, dice él. Deberías estarlo. Ambientada durante un año escolar en 1986, esta es la historia de dos inadaptados destinados a encontrarse: lo suficientemente inteligentes como para saber que el primer amor casi nunca dura, pero lo suficientemente valientes y desesperados como para intentarlo. Cuando Eleanor conoce a Park, recordarás tu primer amor y lo fuerte que te arrastró. ¡Un Best Seller del New York Times! Un libro de honor Michael L. Printz 2014 por la excelencia en literatura para jóvenes adultos Eleanor & Park es ganador del Premio al Mejor Libro de Ficción del Boston Globe Horn Book Award 2013. Un mejor libro infantil de Publishers Weekly en 2013 Un libro infantil notable del New York Times Book Review en 2013 Un mejor libro para adolescentes de Kirkus Reviews en 2013 Un mejor libro de NPR en 2013"),
                4.0
            )
        )

        val predefTerror = listOf(
            Book(
                "KiszDwAAQBAJ",
                "It",
                "Stephen King",
                "http://books.google.com/books/content?id=KiszDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Fiction",
                "Simon and Schuster",
                1184,
                formatSinopsis("Incluye una selección de Sleeping Beauties de Stephen y Owen King después de la página 1157 (a publicarse en septiembre de 2017)."),
                4.5
            ),
            Book(
                "kwKmEAAAQBAJ",
                "Dracula",
                "Bram Stoker",
                "http://books.google.com/books/content?id=kwKmEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Fiction",
                "BoD – Books on Demand",
                482,
                formatSinopsis("Jonathan Harker, a newly qualified English solicitor, takes a business trip to stay at the castle of a Transylvanian nobleman, Count Dracula in the Carpathian Mountains to help the Count purchase a house near London. Harker escapes the castle after discovering that Dracula is a vampire, and the Count moves to England and plagues the seaside town of Whitby. A small group, led by Abraham Van Helsing, hunt Dracula in order to kill him. Dracula is one of the most famous pieces of English literature."),
                4.9
            ),
            Book(
                "Y7sZEAAAQBAJ",
                "Los ritos del agua",
                "Eva García Sáenz de Urturi",
                "http://books.google.com/books/content?id=Y7sZEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Terror",
                "VINTAGE ESPAÑOL",
                478,
                formatSinopsis("Tras El silencio de la ciudad blanca, llega la esperada segunda parte de la trilogía. Ana Belén Liaño, la primera novia de Kraken, aparece asesinada. La mujer estaba embarazada y fue ejecutada según un ritual de hace 2600 años: quemada, colgada y sumergida en un caldero de la Edad del Bronce. 1992. Unai y sus tres mejores amigos trabajan en la reconstrucción de un poblado cántabro. Allí conocen a una enigmática dibujante de cómics, a la que los cuatro consideran su primer amor. 2016. Kraken debe detener a un asesino que imita los Ritos del Agua en lugares sagrados del País Vasco y Cantabria cuyas víctimas son personas que esperan un hijo. La subcomisaria Diaz de Salvatierra está embarazada, pero sobre la paternidad se cierne una duda de terribles consecuencias. Si Kraken es el padre, se convertirá en uno más de la lista de amenazados por los Ritos del Agua."),
                4.7
            ),
            Book(
                "ioYfAAAACAAJ",
                "Cementerio de animales",
                "Stephen King",
                "http://books.google.com/books/content?id=cbsZEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Terror",
                "VINTAGE ESPAÑOL",
                456,
                formatSinopsis("Bestseller #1 del New York Times, Cementerio de animales es un clásico “salvaje, potente y perturbador” (The Washington Post Book World) de Stephen King sobre el mal que existe más allá de la tumba. Cuando el Dr. Louis Creed deja Chicago y se traslada con su familia a la idílica ciudad de Ludlow, en Maine, todo en la vida parece sonreírles. Pero en ese paisaje idílico un peligro oculto acecha a la comunidad. Louis estaba seguro: el gato había muerto atropellado, y por eso lo había enterrado. Aun así, incomprensiblemente, el gato había vuelto a casa. Church estaba allí otra vez, como él temía y al mismo tiempo deseaba, porque su hijita Ellie le había pedido que cuidara de él. Louis lo había enterrado más allá del cementerio de animales. Sin embargo, Church había regresado, y sus ojos eran más crueles y perversos que antes. Aunque volvía a estar allí y Ellie no lo lamentaría, el Dr. Louis Creed sí lo haría. Porque más allá del cementerio de animales, más allá de la valla de troncos que nadie se atrevía a traspasar, el maligno poder del antiguo cementerio indio le atraía con promesas seductoras y tentaciones impías... Allí se esconde una verdad escalofriante más aterradora que la muerte misma y horriblemente más poderosa. Como Louis está a punto de descubrir por sí mismo, a veces es mejor estar muerto..."),
                4.3
            ),
            Book(
                "aQsIQgAACAAJ",
                "El resplandor",
                "Stephen King",
                "http://books.google.com/books/content?id=-chAB7NNa-QC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Terror",
                "DEBOLSILLO",
                475,
                formatSinopsis("Un clásico imprescindible del mejor novelista de terror. REDRUM Esa es la palabra que Danny había visto en el espejo. Y, aunque no sabía leer, entendió que era un mensaje de horror. Danny tenía cinco años, y a esa edad poco niños saben que los espejos invierten las imágenes y menos aún saben diferenciar entre realidad y fantasía. Pero Danny tenía pruebas de que sus fantasías relacionadas con el resplandor del espejo acabarían cumpliéndose: REDRUM... MURDER, asesinato. Pero su padre necesitaba aquel trabajo en el hotel. Danny sabía que su madre pensaba en el divorcio y que su padre se obsesionaba con algo muy malo, tan malo como la muerte y el suicidio. Sí, su padre necesitaba aceptar la propuesta de cuidar de aquel hotel de lujo de más de cien habitaciones, aislado por la nieve durante seis meses. Hasta el deshielo iban a estar solos. ¿Solos?... La crítica ha dicho... «El rey del terror.» El País «Terrorífica... ofrece horrores a un ritmo intenso e infatigable.» The New York Times"),
                4.0
            ),
            Book(
                "4b9yDwAAQBAJ",
                "El Silencio de los Corderos",
                "Thomas Harris",
                "http://books.google.com/books/content?id=4b9yDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Fiction",
                "Debolsillo",
                480,
                formatSinopsis("El FBI encomienda a la joven y brillante Clarice Starling la misión de entrevistarse con Hannibal Lecter, brillante psiquiatra y despiadado asesino, para conseguir su colaboración en la resolución de un caso de asesinatos en serie. El asombroso poder de Lecter es evidente desde el primer momento, pero Starling, sola e indefensa en un mundo desconocido para ella, no se da cuenta de hasta qué punto llegará el juego de ingenio entre ambos."),
                4.7
            ),
            Book(
                "C8NVhWNU_uIC",
                "Coraline",
                "Neil Gaiman",
                "http://books.google.com/books/content?id=C8NVhWNU_uIC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Fiction",
                "A&C Black",
                192,
                formatSinopsis("Cuando una joven niña se aventura a través de una puerta oculta, descubre otra vida con sorprendentes similitudes con la suya propia. Coraline se ha mudado a una nueva casa con sus padres y está fascinada por el hecho de que su 'casa' en realidad es solo la mitad de una casa. Dividida en apartamentos años atrás, hay una pared de ladrillos detrás de una puerta donde una vez hubo un pasillo. Un día, vuelve a ser un pasillo y la intrépida Coraline se aventura por él. Así comienza un misterio que lleva a Coraline hacia los brazos de padres falsos y una vida que no es del todo correcta. ¿Podrá Coraline escapar? ¿Podrá encontrar a sus verdaderos padres? ¿Volverá la vida a ser igual alguna vez?"),
                4.5
            ),
            Book(
                "wxbmEAAAQBAJ",
                "El retrato de Dorian Gray",
                "Oscar Wilde",
                "http://books.google.com/books/content?id=wxbmEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Fiction",
                "DigiCat",
                264,
                formatSinopsis("Este ebook presenta 'El retrato de Dorian Gray', con un índice dinámico y detallado. Se trata de una novela que escribió Oscar Wilde, publicada en 1890. Se considera una de las últimas obras clásicas de la novela de terror gótica con una fuerte temática faustiana. La novela cuenta la historia de un joven llamado Dorian Gray, retratado por el artista Basilio Hallward, quien queda enormemente impresionado por la belleza física de Dorian y comienza a encapricharse con él, creyendo que esta belleza es la responsable de la nueva forma de su arte. Dorian conoce a Lord Henry Wotton, un amigo de Basil, y empieza a cautivarse por la visión del mundo de Lord Henry. Exponiendo un nuevo tipo de hedonismo, Lord Henry indica que 'lo único que vale la pena en la vida es la belleza, y la satisfacción de los sentidos'. Al darse cuenta de que un día su belleza se desvanecerá, Dorian desea tener siempre la edad de cuando le pintó en el cuadro Basil. El deseo de Dorian se cumple, mientras él mantiene para siempre la misma apariencia del cuadro, la figura en él retratada envejece por él. Oscar Wilde (1854 - 1900) fue un escritor, poeta y dramaturgo irlandés. Wilde es considerado uno de los dramaturgos más destacados del Londres victoriano tardío; además, fue una celebridad de la época debido a su gran y aguzado ingenio. Hoy en día, es recordado por sus epigramas, sus obras de teatro y la tragedia de su encarcelamiento, seguida de su temprana muerte."),
                3.9
            )
        )

        val predefManga = listOf(
            Book(
                "hc8OAwAAQBAJ",
                "Food Wars!",
                "Yuto Tsukuda",
                "http://books.google.com/books/content?id=hc8OAwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Cocina",
                "National Geographic Books",
                203,
                formatSinopsis("La batalla de las preliminares del Clásico de Otoño llega a su apoteósico final. ¿Podrá Soma conjurar una obra maestra de curry lo suficientemente brillante como para superar un plato del maestro de fragancias, Akira Hayama? Este volumen también incluye una historia adicional sobre las aventuras de alguien durante sus perezosas vacaciones de verano."),
                5.0
            ),
            Book(
                "hs1kCgAAQBAJ",
                "Kakegurui - Compulsive Gambler",
                "Homura Kawamoto",
                "http://books.google.com/books/content?id=hs1kCgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Comics & Novelas Gráficas",
                "Yen Press LLC",
                248,
                formatSinopsis("Academia Privada Hyakkaou. Una institución para privilegiados con un plan de estudios muy peculiar. Cuando eres hijo o hija de los más adinerados, no es la destreza atlética ni los conocimientos de libros lo que te mantiene adelante. Es leer a tu oponente, el arte del trato. ¿Qué mejor manera de perfeccionar esas habilidades que con un riguroso plan de juegos de azar? En la Academia Privada Hyakkaou, los ganadores viven como reyes y los perdedores son exprimidos al máximo. ¡Pero cuando Yumeko Jabami se inscribe, les enseñará a estos chicos cómo es realmente ser un gran apostador!"),
                4.6
            ),
            Book(
                "xw2_DwAAQBAJ",
                "Jujutsu Kaisen",
                "Gege Akutami",
                "http://books.google.com/books/content?id=xw2_DwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Comics & Novelas Gráficas",
                "VIZ Media LLC",
                193,
                formatSinopsis("Aunque Yuji Itadori parece un adolescente común, ¡su inmenso poder físico es algo impresionante! Todos los clubes deportivos quieren que se una a ellos, pero Itadori prefiere pasar el rato con los marginados en el Club de Investigación del Ocultismo. Un día, el club logra conseguir un objeto maldito sellado. Pero poco saben del terror que desatarán al romper el sello..."),
                5.0
            ),
            Book(
                "ud3lDwAAQBAJ",
                "Spy x Family",
                "Tatsuya Endo",
                "http://books.google.com/books/content?id=ud3lDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Comics & Novelas Gráficas",
                "VIZ Media LLC",
                217,
                formatSinopsis("Twilight, que no confía en otros, tiene mucho trabajo por delante para conseguir una esposa y un hijo para su misión de infiltrarse en una elitista escuela privada. Lo que no sabe es que la esposa que ha elegido es una asesina y el niño que ha adoptado es un telepático."),
                4.3
            ),
            Book(
                "QwXzDQAAQBAJ",
                "Vinland Saga",
                "Makoto Yukimura",
                "http://books.google.com/books/content?id=QwXzDQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Cómics y Novelas Gráficas",
                "Kodansha Comics",
                392,
                formatSinopsis("POR HONOR Y VENGANZA De niño, Thorfinn se sentaba a los pies del gran Leif Ericson y se emocionaba con relatos salvajes de una tierra lejana al oeste. Pero sus fantasías juveniles fueron destrozadas por una incursión de mercenarios. Criado por los vikingos que asesinaron a su familia, Thorfinn se convirtió en un guerrero aterrador, siempre buscando matar al líder del grupo, Askeladd, y vengar a su padre. Lo que sostiene a Thorfinn durante su prueba son su orgullo en su familia y sus sueños de una tierra fértil hacia el oeste, una tierra sin guerra ni esclavitud... la tierra que Leif llamó Vinland."),
                4.0
            ),
            Book(
                "on8jEAAAQBAJ",
                "Girlfriend, Girlfriend",
                "Hiroyuki",
                "http://books.google.com/books/content?id=on8jEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Cómics y Novelas Gráficas",
                "Kodansha Comics",
                211,
                formatSinopsis("¡Anime que llegó en julio de 2020! Puedes alquilar una novia, ¿pero puedes comprar amor? Abatido por una ruptura, Kazuya alquila a la hermosa y educada Chizuru para una cita. ¡Pero tocar fondo podría ser mucho más bajo de lo que pensaba! Chizuru es mucho más que el rostro bonito y el comportamiento dulce por el que pensó que había pagado... En el Japón de hoy en día, los servicios de 'alquiler' pueden ofrecer una tarde con un 'amigo', un 'padre', ¡incluso una novia falsa! Después de una traición impactante por parte de su novia, el desafortunado estudiante de primer año Kazuya se vuelve lo suficientemente desesperado como para intentarlo. Pero rápidamente descubre lo complicado que puede ser 'alquilar' una conexión emocional, ¡y su nueva 'novia', que intenta mantener en secreto su trabajo secundario, entrará en pánico cuando descubra que su vida real y la de Kazuya están entrelazadas de maneras sorprendentes! La familia, la escuela y la vida comienzan a desmoronarse... ¡Es el encuentro entre un chico dulce pero ingenuo y una chica linda pero implacable en esta comedia romántica de manga del siglo XXI!"),
                2.5
            ),
            Book(
                "Au4rAwAAQBAJ",
                "Black Butler, Vol. 1",
                "Yana Toboso",
                "http://books.google.com/books/content?id=Au4rAwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Cómics y Novelas Gráficas",
                "Yen Press LLC",
                182,
                formatSinopsis("A solo un paso de Londres se encuentra la mansión del ilustre condado Phantomhive y su amo, Ciel Phantomhive. El Conde Phantomhive es un gigante en el mundo del comercio, fiel sirviente de la Reina Victoria... y un niño de doce años. Afortunadamente, su leal mayordomo, Sebastian, está siempre a su lado, listo para cumplir los deseos del joven amo. Y ya sea que Sebastian sea llamado para salvar una cena que ha salido mal o para investigar los oscuros secretos del submundo de Londres, aparentemente no hay nada que Sebastian no pueda hacer. De hecho, uno podría incluso decir que Sebastian es demasiado bueno para ser verdad... o al menos, demasiado bueno para ser humano..."),
                3.5
            ),
            Book(
                "3O-TOwAACAAJ",
                "Bleach, Vol. 1 (Library Edition)",
                "Tite Kubo",
                "http://books.google.com/books/content?id=3O-TOwAACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
                "Cómics y Novelas Gráficas",
                "VIZ Media LLC",
                192,
                formatSinopsis("Ichigo Kurosaki tiene habilidades de artes marciales y la capacidad de ver fantasmas, y su vida está a punto de cambiar cuando conoce a Rukia Kuchiki, una segadora de almas y protectora de inocentes."),
                3.4
            )
        )

        val predefAventura = listOf(
            Book(
                "8FjODwAAQBAJ",
                "Los Juegos del Hambre 1 - Los Juegos del Hambre",
                "Suzanne Collins",
                "http://books.google.com/books/content?id=8FjODwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Young Adult Fiction",
                "Molino",
                332,
                formatSinopsis( "El mundo está observando. Ganar significa fama y riqueza. Perder significa una muerte segura. ¡Que empiecen los septuagésimo cuartos juegos del hambre! LA SAGA CON MÁS DE 100 MILLONES DE LIBROS VENDIDOS EN TODO EL MUNDO. En una oscura versión del futuro próximo, doce chicos y doce chicas se ven obligados a participar en un reality show llamado Los Juegos del Hambre. Solo hay una regla: matar o morir. Cuando Katniss Everdeen, una joven de dieciséis años se presenta voluntaria para ocupar el lugar de su hermana en los juegos, lo entiende como una condena a muerte. Sin embargo, Katniss ya ha visto la muerte de cerca y la supervivencia forma parte de su naturaleza. Reseñas: «No pude parar de leer... Este libro es adictivo». Stephen King « Los Juegos del Hambre es increíble». Stephenie Meyer"),
                5.0
            ),
            Book(
                "82LODwAAQBAJ",
                "Divergente 1 - Divergente",
                "Veronica Roth",
                "http://books.google.com/books/content?id=82LODwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Young Adult Fiction",
                "Molino",
                380,
                formatSinopsis("Una elección que delimita quiénes son tus amigos. Una elección que define tus creencias. Una elección que determina tus lealtades... para siempre. Una sola elección puede transformarte. En el Chicago distópico de Beatrice, la sociedad está dividida en cinco facciones. En una ceremonia anual, todos los chicos de dieciséis años deben decidir a qué facción dedicarán el resto de sus vidas. Beatrice tiene que elegir entre quedarse con su familia... y ser quien realmente es. Así que toma una decisión que sorprenderá a todo el mundo, incluida ella..."),
                4.7
            ),
            Book(
                "BG3P0sFxnT4C",
                "Insurgente",
                "Veronica Roth",
                "http://books.google.com/books/content?id=BG3P0sFxnT4C&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Young Adult Fiction",
                "Harper Collins",
                413,
                formatSinopsis("One choice can destroy you. Veronica Roth's second #1 New York Times bestseller continues the dystopian thrill ride that began in Divergent. A hit with both teen and adult readers, Insurgent is the action-packed, emotional adventure that inspired the major motion picture starring Shailene Woodley, Theo James, Ansel Elgort, and Octavia Spencer. As war surges in the factions of dystopian Chicago all around her, Tris attempts to save those she loves—and herself—while grappling with haunting questions of grief and forgiveness, identity and loyalty, politics and love. And don't miss The Fates Divide, Veronica Roth's powerful sequel to the bestselling Carve the Mark!"),
                4.5
            ),
            Book(
                "_dHJAAAACAAJ",
                "El Alquimista",
                "Paulo Coelho",
                "http://books.google.com/books/content?id=_dHJAAAACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api",
                "Alchemists",
                "HarperOne",
                189,
                formatSinopsis("Cuando una persona desea realmente algo, el Universo entero conspira para que pueda realizar su sueño. Basta con aprender a escuchar los dictados del corazón y a descifrar un lenguaje que está más allá de las palabras, el que muestra aquello que los ojos no pueden ver. 'El Alquimista' relata las aventuras de Santiago, un joven pastor andaluz que un día abandona su rebaño para ir en pos de una quimera. Con este enriquecedor viaje por las arenas del desierto, Paulo Coelho recrea un símbolo hermoso y revelador de la vida, el hombre y sus sueños. Considerado ya un clásico de nuestros días, 'El Alquimista' ha cautivado a millones de lectores de todo el mundo y ha supuesto la consagración literaria de Paulo Coelho, uno de los grandes escritores de nuestro tiempo. Destinado al niño que todos llevamos dentro, y frecuentemente comparado con 'El Principito' de Saint-Exupéry y con 'Juan Salvador Gaviota' de Richard Bach, 'El Alquimista' es una obra maestra de la que se han dicho cosas tan hermosas como esta: 'Leer El Alquimista es como levantarse al alba para ver salir el sol mientras el resto del mundo todavía duerme' (Anne Carrière)."),
                3.5
            ),
            Book(
                "jt_JtAEACAAJ",
                "Parque Jurásico",
                "Michael Crichton",
                "http://books.google.com/books/content?id=jt_JtAEACAAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Fiction",
                "Turtleback",
                306,
                formatSinopsis("Cloned dinosaurs wreak havoc in an amusement park."),
                3.7
            ),
            Book(
                "A-y-DwAAQBAJ",
                "El ladrón del rayo (Percy Jackson y los dioses del Olimpo 1)",
                "Rick Riordan",
                "http://books.google.com/books/content?id=A-y-DwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Young Adult Fiction",
                "SALAMANDRA INFANTIL Y JUVENIL",
                299,
                formatSinopsis("El ladrón del rayo da comienzo a la serie «Percy Jackson y los Dioses del Olimpo». Acompaña a Percy a través de esta apasionante serie de aventuras sobre un mundo secreto, el mundo que los antiguos dioses griegos han recreado a nuestro alrededor en pleno siglo XXI. ¿Qué pasaría si un día descubrieras que, en realidad, eres hijo de un dios griego que debe cumplir una misión secreta? Pues eso es lo que le sucede a Percy Jackson, que a partir de ese momento se dispone a vivir los acontecimientos más emocionantes de su vida. Expulsado de seis colegios, Percy padece dislexia y dificultades para concentrarse, o al menos ésa es la versión oficial. Objeto de burlas por inventarse historias fantásticas, ni siquiera él mismo acaba de creérselas hasta el día que los dioses del Olimpo le revelan la verdad: Percy es nada menos que un semidiós, es decir, el hijo de un dios y una mortal. Y como tal ha de descubrir quién ha robado el rayo de Zeus y así evitar que estalle una guerra entre los dioses. Para cumplir la misión contará con la ayuda de sus amigos Grover, un joven sátiro, y Annabeth, hija de Atenea. ** Más vendidos del The New York Times y Publishers Weekly . ** Llevada a la gran pantalla a cargo de Chris Columbus, director de las dos primeras entregas de «Harry Potter». Sobre «Percy Jackson y los Dioses del Olimpo» se ha dicho: «No sigas buscando al nuevo Harry Potter: descubre a Percy Jackson, como lo han hecho ya legiones de admiradores.» Kirkus Reviews"),
                5.0
            ),
            Book(
                "X8fXSsuPM0gC",
                "La ladrona de libros",
                "Markus Zusak",
                "http://books.google.com/books/content?id=X8fXSsuPM0gC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Fiction",
                "DEBOLS!LLO",
                488,
                formatSinopsis("Una novela preciosa, tremendamente humana y emocionante, que describe las peripecias de una niña alemana de nueve años desde que es dada en adopción por su madre hasta el final de la II Guerra Mundial. UNO DE LOS 30 MEJORES NOVELAS HISTÓRICAS DE TODOS LOS TIEMPOS SEGÚN ELLE Érase una vez un pueblo donde las noches eran largas y la muerte contaba su propia historia..."),
                4.5
            ),
            Book(
                "qRID-2-D_kwC",
                "El hogar de Miss Peregrine para niños peculiares",
                "Ransom Riggs",
                "http://books.google.com/books/content?id=qRID-2-D_kwC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Juvenile Fiction",
                "Grupo Planeta Spain",
                367,
                formatSinopsis("El hogar de Miss Peregrine para niños peculiares es una enigmática historia sobre niños extraordinarios y monstruos oscuros; una fantasía escalofriante ilustrada con inquietantes fotografías vintage que deleitará a jóvenes y adultos. De niño, Jacob creó un vínculo muy especial con su abuelo, que le contaba extrañas historias y le enseñaba fotografías de niñas levitando y niños invisibles. Ahora, siguiendo la pista de una misteriosa carta, emprende un viaje hacia la isla remota de Gales en la que su abuelo se crió. Allí, encuentra vivos a los niños y niñas de las fotografías aunque los lugareños afirman que murieron hace muchos años."),
                4.5
            )
        )

        val predefPsicologicos = listOf(
            Book(
                "eMiLDwAAQBAJ",
                "Las 48 leyes del poder",
                "Robert Greene",
                "http://books.google.com/books/content?id=eMiLDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Business & Economics",
                "Océano",
                948,
                formatSinopsis("El best seller mundial para los que quieren obtener, estudiar o combatir el poder absoluto. Amoral, inmisericorde, despiadada y, sobre todo, muy instructiva, esta incisiva obra concentra tres mil años de historia del poder en cuarenta y ocho leyes claras y concisas. Robert Greene detalla las leyes del poder en su esencia más cruda, sintetizando el pensamiento de Maquiavelo, Sun Tzu, Carl von Clausewitz y otros grandes teóricos y estrategas. Algunas leyes sugieren la prudencia (\"Ley n° 1: nunca le haga sombra a su amo\"); otras, el sigilo (\"Ley n° 3: disimule sus intenciones\"); otras más, una total falta de piedad (\"Ley n° 15: aplaste por completo a su enemigo\"). Pero, nos guste o no, todas tienen aplicaciones en la vida cotidiana. Ilustradas mediante anécdotas de Isabel II, Henry Kissinger, P.T. Barnum y otras figuras que han esgrimido el poder (o que lo han padecido), estas leyes fascinarán a cualquiera que se interese en todas las manifestaciones del control total."),
                3.5
            ),
            Book(
                "XfFvDwAAQBAJ",
                "Atomic Habits",
                "James Clear",
                "http://books.google.com/books/content?id=XfFvDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Business & Economics",
                "Penguin",
                322,
                formatSinopsis("The #1 New York Times bestseller. Over 10 million copies sold! Tiny Changes, Remarkable Results..."),
                3.5
            ),
            Book(
                "vOb1EAAAQBAJ",
                "How to Win Friends and Influence People",
                "Dale Carnegie",
                "http://books.google.com/books/content?id=vOb1EAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Business & Economics",
                "ببلومانيا للنشر والتوزيع",
                304,
                formatSinopsis("You can go after the job you want…and get it! You can take the job you have…and improve it!..."),
                4.7
            ),
            Book(
                "reSgAAAAQBAJ",
                "El poder del ahora",
                "Eckhart Tolle",
                "http://books.google.com/books/content?id=reSgAAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Body, Mind & Spirit",
                "GRIJALBO",
                202,
                formatSinopsis("En este libro el autor nos comparte cómo se puede alcanzar un estado de iluminación aquí y ahora; y que es posible vivir libre del sufrimiento, de la ansiedad y de la neurosis. Más de 4,000,000 ejemplares vendidos. El Bestseller #1 del New York Times. El clásico que consagró a Eckhart Tolle como uno de los gurús más importantes del mundo. El poder del ahora es un libro único. Tiene la capacidad de crear una experiencia en los lectores y de cambiar su vida. Hoy ya es considerado una obra maestra. \"Uno de los mejores libros de los últimos años. Cada frase evoca verdad y poder.\" Deepak Chopra Para lograr la iluminación aquí y ahora sólo tenemos que comprender nuestro papel de creadores de nuestro dolor. Es nuestra propia mente la que causa nuestros problemas con su corriente constante de pensamientos, aferrándose al pasado, preocupándose por el futuro. Cometemos el error de identificarnos con ella, de pensar que eso es lo que somos, cuando de hecho somos seres mucho más grandes. Escrito en un formato de preguntas y respuestas que lo hace muy accesible, El poder del ahora es una invitación a la reflexión, que le abrirá las puertas a la plenitud espiritual y le permitirá ver la vida con nuevos ojos y empezar a disfrutar del verdadero poder del ahora."),
                5.0
            ),
            Book(
                "Nh9PDwAAQBAJ",
                "El Club de las 5 AM",
                "Robin Sharma",
                "http://books.google.com/books/content?id=Nh9PDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Autoayuda",
                "HarperCollins",
                383,
                formatSinopsis("El legendario experto en liderazgo y rendimiento de élite, Robin Sharma, introdujo el concepto del Club de las 5 AM hace más de veinte años, basado en una rutina matutina revolucionaria que ha ayudado a sus clientes a maximizar su productividad, activar su mejor salud y blindar su serenidad en esta era de complejidad abrumadora. Ahora, en este libro que cambiará tu vida, creado por el autor durante un riguroso período de cuatro años, descubrirás el hábito de levantarte temprano que ha ayudado a tantos a lograr resultados épicos mientras mejoran su felicidad, utilidad y sensación de vivacidad. A través de una historia encantadora y a menudo divertida sobre dos extraños que luchan y conocen a un excéntrico magnate que se convierte en su mentor secreto, El Club de las 5 AM te guiará sobre: Cómo los grandes genios, titanes de negocios y las personas más sabias del mundo comienzan sus mañanas para lograr logros asombrosos Una fórmula poco conocida que puedes usar de inmediato para despertarte temprano sintiéndote inspirado, centrado y lleno de un impulso ardiente para aprovechar al máximo cada día Un método paso a paso para proteger las horas más tranquilas del amanecer para que tengas tiempo para hacer ejercicio, renovarte y crecer personalmente Una práctica basada en la neurociencia probada para ayudar a levantarte fácilmente mientras la mayoría de las personas duermen, dándote tiempo precioso para ti mismo para pensar, expresar tu creatividad y comenzar el día pacíficamente en lugar de estar apurado Tácticas exclusivas para defender tus dones, talentos y sueños contra la distracción digital y las diversiones triviales para que disfrutes de la fortuna, la influencia y un impacto magnífico en el mundo Parte manifiesto para el dominio, parte manual para la productividad de grado genial y parte compañero para una vida vivida bellamente, El Club de las 5 AM es una obra que transformará tu vida. Para siempre."),
                5.0
            ),
            Book(
                "mo1xDwAAQBAJ",
                "Rodeado de Idiotas",
                "Thomas Erikson",
                "http://books.google.com/books/content?id=mo1xDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Negocios y Economía",
                "St. Martin's Essentials",
                168,
                formatSinopsis("¿Alguna vez has pensado que eres el único que tiene sentido? ¿O has intentado razonar con tu pareja con resultados desastrosos? ¿Te vuelven loco las respuestas largas y enredadas? ¿O el comportamiento abrasivo de tu colega te irrita? No estás solo. Después de una reunión desastrosa con un emprendedor altamente exitoso, quien estaba genuinamente convencido de que estaba 'rodeado de idiotas', el experto en comunicación y autor de bestsellers, Thomas Erikson, se dedicó a entender cómo funcionan las personas y por qué a menudo luchamos por conectar con ciertos tipos de personas. Rodeado de Idiotas es un fenómeno internacional, con más de 1.5 millones de copias vendidas en todo el mundo. Ofrece un método simple pero revolucionario para evaluar las personalidades de las personas con las que nos comunicamos, dentro y fuera de la oficina, basado en cuatro tipos de personalidades (Rojo, Azul, Verde y Amarillo), y proporciona ideas sobre cómo podemos ajustar la forma en que hablamos y compartimos información. Erikson te ayudará a entenderte mejor a ti mismo, perfeccionar tus habilidades de comunicación y sociales, manejar conflictos con confianza, mejorar la dinámica con tu jefe y equipo, y sacar lo mejor de las personas con las que tratas y gestionas. También comparte trucos simples sobre lenguaje corporal, mejora de la comunicación escrita, consejos sobre cuándo retroceder o cuándo insistir, y cuándo hablar o callar. Lleno de momentos de '¡ahá!' y '¡oh no!', Rodeado de Idiotas te ayudará a entender y comunicarte con quienes te rodean, incluso personas que actualmente piensas que están más allá de toda comprensión. ¡Y con un poco de suerte, también puedes estar seguro de que el idiota no eres tú!"),
                5.0
            ),
            Book(
                "UmDJoxcXU5gC",
                "El arte de la guerra",
                "Sun-Tzu",
                "http://books.google.com/books/content?id=UmDJoxcXU5gC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Historia",
                "Editorial Fundamentos",
                172,
                formatSinopsis("El arte de la guerra es un tratado militar escrito por el estratega chino Sun Tzu en el siglo V a.C. Es considerado uno de los libros más importantes sobre estrategia y tácticas militares. En este texto, Sun Tzu expone principios fundamentales para la guerra, incluyendo estrategias, tácticas, y reflexiones sobre cómo enfrentar y vencer al enemigo. Su enfoque no solo se limita a cuestiones militares, sino que también ofrece perspectivas valiosas sobre liderazgo, planificación y toma de decisiones en situaciones competitivas. El arte de la guerra ha sido objeto de estudio y aplicación en diversas áreas, incluyendo los negocios y la gestión empresarial, debido a su relevancia en estrategias de competencia y confrontación."),
                4.5
            ),
            Book(
                "5VYAAwAAQBAJ",
                "The Art of Thinking Clearly",
                "Rolf Dobelli",
                "http://books.google.com/books/content?id=5VYAAwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Psicología",
                "Harper Collins",
                384,
                formatSinopsis("Un pensador de clase mundial enumera las 100 maneras en las que los humanos se comportan irracionalmente, mostrándonos qué podemos hacer para reconocer y minimizar estos 'errores de pensamiento' para tomar mejores decisiones y tener una mejor vida. A pesar de las mejores intenciones, los humanos somos notoriamente malos, es decir, irracionales, cuando se trata de tomar decisiones y evaluar riesgos e intercambios. Los psicólogos y neurocientíficos se refieren a estas debilidades humanas distintas, sesgos y trampas de pensamiento como 'errores cognitivos'. Los errores cognitivos son desviaciones sistemáticas de la racionalidad, del pensamiento y comportamiento lógico y racional optimizados. Cometemos estos errores todo el tiempo, en todo tipo de situaciones, para problemas grandes y pequeños: ya sea elegir la manzana o el pastel; si mantener los fondos de jubilación en el mercado de valores cuando el Dow cae, o si seguir el consejo de un amigo en lugar de un desconocido. El 'giro conductual' en neurociencia y economía en los últimos veinte años ha aumentado nuestra comprensión de cómo pensamos y cómo tomamos decisiones. Muestra cómo los errores sistemáticos afectan nuestro pensamiento y en qué condiciones funcionan mejor y peor nuestros procesos de pensamiento. La psicología evolutiva ofrece teorías convincentes sobre por qué nuestro pensamiento está, de hecho, defectuoso. Las neurociencias pueden precisar con mayor precisión qué sucede exactamente cuando pensamos claramente y cuándo no lo hacemos. Basado en este amplio cuerpo de investigación, The Art of Thinking Clearly es una presentación entretenida de estos conocidos errores sistemáticos de pensamiento, ofreciendo orientación y perspicacia en todo, desde por qué no deberías aceptar una bebida gratis hasta por qué DEBERÍAS salir de una película que no te guste, por qué es tan difícil predecir el futuro hasta por qué no debes ver las noticias. El libro está organizado en 100 capítulos cortos, cada uno cubriendo un solo error cognitivo, sesgo o heurística. Ejemplos de estos conceptos incluyen: reciprocidad, sesgo de confirmación, la trampa de 'mejora antes de empeorar' y la tendencia del hombre con un martillo. En prosa atractiva y con ejemplos y anécdotas del mundo real, The Art of Thinking Clearly ayuda a resolver el rompecabezas del razonamiento humano."),
                5.0
            )
        )

        val predefCienciaFiccion = listOf(
            Book(
                "ydQiDQAAQBAJ",
                "Dune",
                "Frank Herbert",
                "http://books.google.com/books/content?id=ydQiDQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Ficción",
                "Penguin",
                722,
                formatSinopsis("Ahora una GRAN PELÍCULA dirigida por Denis Villeneuve y protagonizada por Timothée Chalamet, Zendaya, Jason Momoa, Rebecca Ferguson, Oscar Isaac, Josh Brolin, Stellan Skarsgård, Dave Bautista, Stephen McKinley Henderson, Chang Chen, Charlotte Rampling y Javier Bardem. Una edición de lujo en tapa dura del libro de ciencia ficción más vendido de todos los tiempos, parte de Penguin Galaxy, una serie coleccionable de seis clásicos de ciencia ficción/fantasía, con una introducción de serie escrita por Neil Gaiman. Ganador del concurso AIGA + Design Observer 50 Books | 50 Covers. La obra maestra suprema de la ciencia ficción, Dune siempre será considerada un triunfo de la imaginación. Ambientada en el planeta desierto Arrakis, es la historia del niño Paul Atreides, quien se convertirá en el misterioso hombre conocido como Muad'Dib. La noble familia de Paul es nombrada administradora de Arrakis, cuyas arenas son la única fuente de una poderosa droga llamada 'la especia'. Después de que su familia es derrocada en una trama traidora, Paul debe infiltrarse para vengarse y llevar a cabo el sueño más antiguo e inalcanzable de la humanidad. Una impresionante mezcla de aventura y misticismo, ecologismo y política, Dune ganó el primer Premio Nébula, compartió el Premio Hugo y formó la base de lo que es sin duda la más grandiosa epopeya de la ciencia ficción."),
                4.0
            ),
            Book(
                "kSVFAAAAQBAJ",
                "The Blade Runner Experience",
                "Will Brooker",
                "http://books.google.com/books/content?id=kSVFAAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Artes escénicas",
                "Columbia University Press",
                426,
                formatSinopsis("Desde su lanzamiento en 1982, Blade Runner de Ridley Scott, basada en la novela de Philip K. Dick ¿Sueñan los androides con ovejas eléctricas?, ha permanecido como un clásico de culto gracias a su representación de un Los Ángeles futurista, su trama compleja y enigmática, y sus preguntas subyacentes sobre la naturaleza de la identidad humana. The Blade Runner Experience: The Legacy of a Science Fiction Classic examina la película en un contexto amplio, analizando su relación con la novela original, el juego de PC, la serie de secuelas y las muchas películas influenciadas por su estilo y temas. Investiga el fandom en línea de Blade Runner y pregunta cómo la ciudad futurista de la película se compara con el Los Ángeles actual, y revisita la película para plantear nuevas preguntas sorprendentes sobre sus personajes y su mundo."),
                4.0
            ),
            Book(
                "9tMePwAACAAJ",
                "Ghost in the Shell",
                "Masamune Shirow",
                "http://books.google.com/books/content?id=9tMePwAACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
                "Libro",
                "Titan Books (UK)",
                368,
                formatSinopsis("En lo profundo del siglo XXI, la línea entre el hombre y la máquina se ha difuminado inexorablemente. En este paisaje que converge rápidamente, la súper agente cíborg Mayor Motoko Kusanagi tiene la tarea de rastrear a los terroristas y ciberdelincuentes más peligrosos, incluidos los \"hackers fantasma\", capaces de explotar la interfaz hombre/máquina reprogramando mentes humanas para convertirlas en títeres que lleven a cabo sus fines criminales."),
                4.0
            ),
            Book(
                "QEhWPgAACAAJ",
                "I, Robot",
                "Isaac Asimov",
                "http://books.google.com/books/content?id=QEhWPgAACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
                "Ficción",
                "Spectra",
                224,
                formatSinopsis("La evolución de la tecnología robótica hacia un estado de perfección por parte de civilizaciones futuras se explora en nueve historias de ciencia ficción."),
                4.0
            ),
            Book(
                "enzYEAAAQBAJ",
                "Regreso al futuro",
                "PETER KAPRA",
                "http://books.google.com/books/content?id=enzYEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Ficción",
                "BOLSILIBROS",
                180,
                formatSinopsis("Un astronauta afronta sus últimos días de vida tras ver morir uno a uno a sus compañeros de viaje tras más de medio siglo surcando las estrellas cuando por fin recibe señales de una raza inteligente. En realidad ha vuelto a casa, ¡pero millones de años tras su partida!"),
                0.0
            ),
            Book(
                "1Ij-AgAAQBAJ",
                "El juego de Ender (Saga de Ender 1)",
                "Orson Scott Card",
                "http://books.google.com/books/content?id=1Ij-AgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Ficción",
                "B DE BOOKS",
                252,
                formatSinopsis("La Tierra se ve amenazada por una raza extraterrestre, los Insectores, que se comunican telepáticamente y consideran no tener nada en común con los humanos, a quienes pretenden destruir. Para vencerlos es necesario una nueva clase de genio militar, y por ello se ha permitido el nacimiento de Ender, lo que constituye, en cierta forma, una anomalía, pues es el tercer hijo de una pareja en un mundo que ha limitado estrictamente a dos el número de descendientes. El niño Ender deberá aprender todo lo relativo a la guerra en los videojuegos y en los peligrosos ensayos de batallas espaciales que realiza con sus compañeros. A la habilidad en el tratamiento de las emociones, ya característica de Orson Scott Card, se une en este libro el interés por el empleo de las simulaciones por ordenador y los juegos de fantasía en la formación militar, estratégica y psicológica del protagonista."),
                5.0
            ),
            Book(
                "u4R_FstZDEgC",
                "Hyperion",
                "Dan Simmons",
                "http://books.google.com/books/content?id=u4R_FstZDEgC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Ficción",
                "Spectra",
                532,
                formatSinopsis("A stunning tour de force filled with transcendent awe and wonder, Hyperion is a masterwork of science fiction that resonates with excitement and invention, the first volume in a remarkable epic by the multiple-award-winning author of The Hollow Man. On the world called Hyperion, beyond the reach of galactic law, waits a creature called the Shrike. There are those who worship it. There are those who fear it. And there are those who have vowed to destroy it. In the Valley of the Time Tombs, where huge, brooding structures move backward through time, the Shrike waits for them all. On the eve of Armageddon, with the entire galaxy at war, seven pilgrims set forth on a final voyage to Hyperion seeking the answers to the unsolved riddles of their lives. Each carries a desperate hope—and a terrible secret. And one may hold the fate of humanity in his hands. Praise for Dan Simmons and Hyperion 'Dan Simmons has brilliantly conceptualized a future 700 years distant. In sheer scope and complexity it matches, and perhaps even surpasses, those of Isaac Asimov and James Blish.'—The Washington Post Book World 'An unfailingly inventive narrative . . . generously conceived and stylistically sure-handed.'—The New York Times Book Review 'Simmons’s own genius transforms space opera into a new kind of poetry.'—The Denver Post 'An essential part of any science fiction collection.'—Booklist"),
                5.0
            ),
            Book(
                "q_WnHAAACAAJ",
                "Neuromante",
                "William Gibson",
                "http://books.google.com/books/content?id=q_WnHAAACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
                "Business intelligence",
                "Desconocido",
                316,
                formatSinopsis( "William Gibson cuya primera novela El Neuromante puede llamarse la novela ciberpunk, es la llanta mas refulgente del movimiento. Epica en escala, mezcla alta tecnologia con una sensibilidad de cine negro, una especie de Homero que escribe la Odisea saliendole de microchips."),
                2.5
            )
        )

        //se actualizan los adaptadores de sus listados de libros correspondientes
        listAdapterManga.updateList(predefManga)
        listAdapterAventura.updateList(predefAventura)
        listAdapterTerror.updateList(predefTerror)
        listAdapterPsicologico.updateList(predefPsicologicos)
        listAdapterCienciaFiccion.updateList(predefCienciaFiccion)
        listAdapterRomance.updateList(predefRomance)
    }


    private fun setupRecyclerView() {

        listAdapterManga = AdapterApiInicio(object : AdapterApiInicio.OnItemClickListener {
            override fun onItemClick(dataItem: Book) {
                //el valor del libro seleccionado (celda) pasa a ser dataItem y se navega hacia la pantalla de detalles del libro escogido
                myViewModel.libro_seleccionado.value = dataItem
                findNavController().navigate(R.id.action_fragmentApiInicio_to_fragmentLibroApi)
            }
        })
        listAdapterAventura = AdapterApiInicio(object : AdapterApiInicio.OnItemClickListener {
            override fun onItemClick(dataItem: Book) {
                myViewModel.libro_seleccionado.value = dataItem
                findNavController().navigate(R.id.action_fragmentApiInicio_to_fragmentLibroApi)
            }
        })
        listAdapterTerror = AdapterApiInicio(object : AdapterApiInicio.OnItemClickListener {
            override fun onItemClick(dataItem: Book) {
                myViewModel.libro_seleccionado.value = dataItem
                findNavController().navigate(R.id.action_fragmentApiInicio_to_fragmentLibroApi)
            }
        })
        listAdapterPsicologico = AdapterApiInicio(object : AdapterApiInicio.OnItemClickListener {
            override fun onItemClick(dataItem: Book) {
                myViewModel.libro_seleccionado.value = dataItem
                findNavController().navigate(R.id.action_fragmentApiInicio_to_fragmentLibroApi)
            }
        })
        listAdapterCienciaFiccion = AdapterApiInicio(object : AdapterApiInicio.OnItemClickListener {
            override fun onItemClick(dataItem: Book) {
                myViewModel.libro_seleccionado.value = dataItem
                findNavController().navigate(R.id.action_fragmentApiInicio_to_fragmentLibroApi)
            }
        })
        listAdapterRomance = AdapterApiInicio(object : AdapterApiInicio.OnItemClickListener {
            override fun onItemClick(dataItem: Book) {
                myViewModel.libro_seleccionado.value = dataItem
                findNavController().navigate(R.id.action_fragmentApiInicio_to_fragmentLibroApi)
            }
        })


        //se configuran los layoutManager y los adaptadores para cada RecyclerView
        binding.rvManga.layoutManager = LinearLayoutManager(requireContext(),   RecyclerView.HORIZONTAL, false)
        binding.rvManga.adapter = listAdapterManga

        binding.rvAventura.layoutManager = LinearLayoutManager(requireContext(),   RecyclerView.HORIZONTAL, false)
        binding.rvAventura.adapter = listAdapterAventura

        binding.rvTerror.layoutManager = LinearLayoutManager(requireContext(),   RecyclerView.HORIZONTAL, false)
        binding.rvTerror.adapter = listAdapterTerror

        binding.rvPsicologico.layoutManager = LinearLayoutManager(requireContext(),   RecyclerView.HORIZONTAL, false)
        binding.rvPsicologico.adapter = listAdapterPsicologico

        binding.rvCienciaFiccion.layoutManager = LinearLayoutManager(requireContext(),   RecyclerView.HORIZONTAL, false)
        binding.rvCienciaFiccion.adapter = listAdapterCienciaFiccion

        binding.rvRomance.layoutManager = LinearLayoutManager(requireContext(),   RecyclerView.HORIZONTAL, false)
        binding.rvRomance.adapter = listAdapterRomance
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

