import scrapy


class JohnnySeedsCategoriesSpider(scrapy.Spider):
    name = "categories_spider"
    start_urls = [
        'https://www.johnnyseeds.com/vegetables/?start=0&sz=18',
        'https://www.johnnyseeds.com/fruits/?start=0&sz=18',
        'https://www.johnnyseeds.com/herbs/?start=0&sz=18'
    ]

    custom_settings = {
        'FEED_FORMAT': 'json',
        'FEED_URI': 'json-output/categories.json'
    }

    def parse(self, response):
        SEED_SELECTOR = '.grid-tile'
        for seed in response.css(SEED_SELECTOR):

            NAME_SELECTOR = 'a.name-link ::text'
            IMAGE_SELECTOR = 'img ::attr(src)'
            LINK_SELECTOR = 'a ::attr(href)'
            yield {
                'name': seed.css(NAME_SELECTOR).get(),
                'image': seed.css(IMAGE_SELECTOR).get(),
                'link': seed.css(LINK_SELECTOR).get(),
            }

        NEXT_PAGE_SELECTOR = '.c-pagination a.c-pagination__link ::attr(href)'
        next_page_list = response.css(NEXT_PAGE_SELECTOR).getall()
        if next_page_list:
            for x in next_page_list:
                yield scrapy.Request(
                    response.urljoin(x),
                    callback=self.parse
                )
