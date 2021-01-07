import json
import scrapy


class JohnnySeedsSeedsIndividualSeed(scrapy.Spider):
    name = "individual_seeds_spider"

    start_urls = [
        "https://www.johnnyseeds.com/fruits/strawberry/elan-f1-strawberry-seed-3893.html?cgid=strawberry"]

    custom_settings = {
        'FEED_FORMAT': 'json',
        'FEED_URI': 'json-output/strawberry-seeds.json'
    }

    def parse(self, response):
        # TODO Implement this scraper
        NAME_SELECTOR = '.product-name ::text'
        SECONDARY_NAME_SELECTOR = '.c-product-header__subheading ::text'
        DESCRIPTION_SELECTOR = '.c-tile__description ::text'
        IMAGE_SELECTOR = 'img.c-image-gallery__main-image ::attr(src)'
        LINK_SELECTOR = 'a.thumb-link ::attr(href)'
        factTerms = response.css('.c-facts .c-facts__term h3 ::text').getall()
        factDefinitions = response.css(
            '.c-facts .c-facts__definition h4 ::text').getall()

        daysToMaturity = ""

        for num, fact in enumerate(factTerms, start=0):
            print(num)
            if "Maturity" in fact:
                print("found")
                daysToMaturity = factDefinitions[num]

        yield {
            'name': response.css(NAME_SELECTOR).get().replace('\n', ''),
            'maturity': daysToMaturity
        }
