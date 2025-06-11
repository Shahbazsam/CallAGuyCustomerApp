package com.example.callaguy.presentation.Service.data

import com.example.callaguy.R
import com.example.callaguy.presentation.Service.model.FeaturedOfferItem

val featuredOffers = listOf(
    FeaturedOfferItem(
        id = 1,
        title = "20% off on Cleaning",
        subtitle = "Book now and save",
        imageUrl = R.drawable.featured_cleaning,
        description = "Enjoy 20% off your next home cleaning service. Whether you need a deep clean or just a freshen-up, our professionals deliver sparkling results. Limited-time offer—book today!"
    ),
    FeaturedOfferItem(
        id = 2,
        title = "Free plumbing check this week",
        subtitle = "Limited time offer",
        imageUrl = R.drawable.featured_plumbing,
        description = "Prevent costly plumbing issues with a free inspection this week. Our team will assess pipes, faucets, and drains to catch problems early—completely free!"
    ),
    FeaturedOfferItem(
        id = 3,
        title = "Book now, pay later",
        subtitle = "Flexible payment options",
        imageUrl = R.drawable.featured_paylater,
        description = "Need repairs now but want to delay payment? We’ve got you covered. Choose our flexible 'Book Now, Pay Later' option for most services. No interest, no hassle."
    ),
    FeaturedOfferItem(
        id = 4,
        title = "Summer home maintenance",
        subtitle = "Prepare for the season",
        imageUrl = R.drawable.featured_summer,
        description = "Prepare your home for summer with our seasonal maintenance package. From AC checks to outdoor cleaning, we’ll help you get ready for sunny days ahead."
    ),
    FeaturedOfferItem(
        id = 5,
        title = "Winterize your home",
        subtitle = "Protect your home",
        imageUrl = R.drawable.featured_winter,
        description = "Avoid winter disasters like frozen pipes and roof leaks. Our winterization package includes sealing, insulation checks, and system readiness to keep you warm and safe."
    )
)
