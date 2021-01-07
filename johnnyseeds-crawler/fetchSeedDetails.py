import json
import scrapy


class JohnnySeedsSeedsDetailedSeeds(scrapy.Spider):
    name = "detailed_seeds_spider"

    start_urls = []

    with open('json-output/basic-seeds.json') as json_file:
        BASE_URL = 'https://www.johnnyseeds.com/'
        data = json.load(json_file)
        for item in data:
            link = item['link']
            start_urls.append(link)

    custom_settings = {
        'FEED_FORMAT': 'json',
        'FEED_URI': 'json-output/detailed-seeds.json'
    }

    def parse(self, response):
        # TODO Implement this scraper
        NAME_SELECTOR = '.product-name ::text'
        SECONDARY_NAME_SELECTOR = '.c-product-header__subheading ::text'
        DESCRIPTION_SELECTOR = '.c-tile__description ::text'
        IMAGE_SELECTOR = 'img.c-image-gallery__main-image ::attr(src)'

        factTerms = response.css('.c-facts .c-facts__term h3 ::text').getall()
        factDefinitions = response.css(
            '.c-facts .c-facts__definition h4 ::text').getall()

        daysToMaturity = ""

        for num, factHeader in enumerate(factTerms, start=0):
            if "maturity" in factHeader.lower():
                # Fix a mispelling I found
                daysToMaturity = factDefinitions[num].replace("thrid", "third")
                # Now parse the dates into something usable

        yield {
            'name': response.css(NAME_SELECTOR).get().replace('\n', ''),
            'maturity': daysToMaturity,
            'secondary_name': response.css(SECONDARY_NAME_SELECTOR).get().replace('\n', ''),
            'description': response.css(DESCRIPTION_SELECTOR).get(),
            'image': response.css(IMAGE_SELECTOR).get(),
            'link': response.url
        }
