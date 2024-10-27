document.addEventListener('DOMContentLoaded', function() {
	var swiper = new Swiper('.section2_swiper', {
		spaceBetween: 7,
		navigation: {
			nextEl: '.swiper-button-next2',
			prevEl: '.swiper-button-prev2',
		},
		centeredSlides: false,
		watchOverflow: true,
		breakpoints: {
			0: {
				slidesPerView: 2.2,
			},
			768: {
				slidesPerView: 4.2,
			},
			1200: {
				slidesPerView: 6.2,
			}
		}
	});

	var swiper = new Swiper('.section3_swiper', {
		spaceBetween: 7,
		navigation: {
			nextEl: '.swiper-button-next3',
			prevEl: '.swiper-button-prev3',
		},
		centeredSlides: false,
		watchOverflow: true,
		breakpoints: {
			0: {
				slidesPerView: 2.2,
			},
			768: {
				slidesPerView: 4.2,
			},
			1200: {
				slidesPerView: 6.2,
			}
		}
	});

});
