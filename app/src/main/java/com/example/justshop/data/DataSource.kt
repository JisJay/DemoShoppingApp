package com.example.justshop.data

import com.example.justshop.R
import com.example.justshop.model.JustShopItem
import com.example.justshop.model.Rating

/**
 * Data Source for JustShop App
 */

var justShopItemList = listOf(
    JustShopItem(id = 1,
        title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
        price = 109.95,
        description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
        category = "men's clothing",
        imageId = R.drawable.item1,
        rating = Rating( rate = 3.9, count= 120)),

    JustShopItem(id = 2,
        title = "Mens Casual Premium Slim Fit T-Shirts ",
        price = 22.3,
        description = "Slim-fitting style, contrast raglan long sleeve, three-button henley placket, light weight & soft fabric for breathable and comfortable wearing. And Solid stitched shirts with round neck made for durability and a great fit for casual fashion wear and diehard baseball fans. The Henley style round neckline includes a three-button placket.",
        category = "men's clothing",
        imageId=  R.drawable.item2,
        rating = Rating( rate = 4.1,count= 259)),

    JustShopItem( id = 3,
        title = "Mens Cotton Jacket",
        price = 55.99,
        description = "great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.",
        category = "men's clothing",
        imageId=  R.drawable.item3,
        rating = Rating ( rate = 4.7,count= 500)),

    JustShopItem(id = 4,
        title = "Mens Casual Slim Fit",
        price = 15.99,
        description = "The color could be slightly different between on the screen and in practice. / Please note that body builds vary by person, therefore, detailed size information should be reviewed below on the product description.",
        category = "men's clothing",
        imageId=R.drawable.item4,
        rating = Rating ( rate = 2.1,count= 430)),

    JustShopItem(id = 5,
        title = "John Hardy Women's Legends Naga Gold & Silver Dragon Station Chain Bracelet",
        price = 695.0,
        description = "From our Legends Collection, the Naga was inspired by the mythical water dragon that protects the ocean's pearl. Wear facing inward to be bestowed with love and abundance, or outward for protection.",
        category = "jewelery",
        imageId= R.drawable.item5,
        rating = Rating ( rate = 4.6,count= 400)),

    JustShopItem(id = 6,
        title = "Solid Gold Petite Micropave ",
        price = 168.0,
        description = "Satisfaction Guaranteed. Return or exchange any order within 30 days.Designed and sold by Hafeez Center in the United States. Satisfaction Guaranteed. Return or exchange any order within 30 days.",
        category = "jewelery",
        imageId= R.drawable.item6,
        rating = Rating ( rate = 3.9,count= 70)),

    JustShopItem(id = 7,
        title = "White Gold Plated Princess",
        price = 9.99,description = "Classic Created Wedding Engagement Solitaire Diamond Promise Ring for Her. Gifts to spoil your love more for Engagement, Wedding, Anniversary, Valentine's Day...",
        category = "jewelery",
        imageId= R.drawable.item7,
        rating = Rating ( rate = 3.0,count= 400)),

    JustShopItem(id = 8,
        title = "Pierced Owl Rose Gold Plated Stainless Steel Double",
        price = 10.99,
        description = "Rose Gold Plated Double Flared Tunnel Plug Earrings. Made of 316L Stainless Steel",
        category = "jewelery",
        imageId= R.drawable.item8,
        rating = Rating ( rate = 1.9,count= 100)),

    JustShopItem(id = 9,
        title = "WD 2TB Elements Portable External Hard Drive - USB 3.0 ",
        price = 64.0,
        description = "USB 3.0 and USB 2.0 Compatibility Fast data transfers Improve PC Performance High Capacity; Compatibility Formatted NTFS for Windows 10, Windows 8.1, Windows 7; Reformatting may be required for other operating systems; Compatibility may vary depending on user’s hardware configuration and operating system",
        category = "electronics",
        imageId= R.drawable.item9,
        rating = Rating ( rate = 3.3,count= 203)),

    JustShopItem(id = 10,
        title = "SanDisk SSD PLUS 1TB Internal SSD - SATA III 6 Gb/s",
        price = 109.0,
        description = "Easy upgrade for faster boot up, shutdown, application load and response (As compared to 5400 RPM SATA 2.5” hard drive; Based on published specifications and internal benchmarking tests using PCMark vantage scores) Boosts burst write performance, making it ideal for typical PC workloads The perfect balance of performance and reliability Read/write speeds of up to 535MB/s/450MB/s (Based on internal testing; Performance may vary depending upon drive capacity, host device, OS and application.)",
        category = "electronics",
        imageId= R.drawable.item10,
        rating = Rating ( rate = 2.9,count= 470))
)
