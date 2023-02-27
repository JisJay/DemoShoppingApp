import com.example.justshop.data.FavItem
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Item] from a given data source.
 */
interface LocalDbItemsRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    suspend fun getAllItems(): List<FavItem>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(id: Int): Flow<FavItem?>

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(item: FavItem)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(item: FavItem)

    /**
     * Update item in the data source
     */
    suspend fun updateItem(item: FavItem)
}