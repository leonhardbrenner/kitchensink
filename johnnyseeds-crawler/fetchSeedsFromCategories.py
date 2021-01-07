import json
import scrapy


class JohnnySeedsSeedsFromCategoriesSpider(scrapy.Spider):
    name = "seeds_from_categories_spider"

    start_urls = []

    with open('json-output/categories.json') as json_file:
        data = json.load(json_file)
        for item in data:
            link = item['link'] + "?start=0&sz=18"
            start_urls.append(link)

    custom_settings = {
        'FEED_FORMAT': 'json',
        'FEED_URI': 'json-output/basic-seeds.json'
    }

    def parse(self, response):
        SEED_SELECTOR = '.grid-tile'
        NAME_SELECTOR = '.product-name ::text'
        SECONDARY_NAME_SELECTOR = '.product-secondary ::text'
        DESCRIPTION_SELECTOR = '.c-tile__description ::text'
        IMAGE_SELECTOR = '.product-image img ::attr(src)'
        LINK_SELECTOR = 'a.thumb-link ::attr(href)'
        BASE_URL = 'https://www.johnnyseeds.com'

        # item = response.meta['item']
        # item['results'] = []
        for seed in response.css(SEED_SELECTOR):
            yield {
                'name': seed.css(NAME_SELECTOR).get().replace('\n', ''),
                'secondary_name': seed.css(SECONDARY_NAME_SELECTOR).get().replace('\n', ''),
                'description': seed.css(DESCRIPTION_SELECTOR).get(),
                'image': seed.css(IMAGE_SELECTOR).get(),
                'link': BASE_URL + seed.css(LINK_SELECTOR).get(),
            }

        NEXT_PAGE_SELECTOR = '.c-pagination a.c-pagination__link ::attr(href)'
        next_page_list = response.css(NEXT_PAGE_SELECTOR).getall()
        if next_page_list:
            for x in next_page_list:
                yield scrapy.Request(
                    response.urljoin(x),
                    callback=self.parse
                )
