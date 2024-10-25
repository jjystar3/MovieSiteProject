document.addEventListener('DOMContentLoaded', function() {
    var swiper = new Swiper('.section2_swiper', {
        spaceBetween: 7, // 슬라이드 간격
        navigation: {
            nextEl: '.swiper-button-next2',
            prevEl: '.swiper-button-prev2',
        },
        centeredSlides: false, // 슬라이드를 왼쪽 정렬
        watchOverflow: true, // 슬라이드가 부족할 경우 네비게이션 숨김
        breakpoints: {
            0: {
                slidesPerView: 2.2, // 작은 화면에서 2.2개 슬라이드 표시
            },
            768: {
                slidesPerView: 4.2, // 중간 화면에서 4.2개 슬라이드 표시
            },
            1200: {
                slidesPerView: 6.2, // 큰 화면에서 6.2개 슬라이드 표시
            }
        }
    });

    var swiper = new Swiper('.section3_swiper', {
        spaceBetween: 7, // 슬라이드 간격
        navigation: {
            nextEl: '.swiper-button-next3',
            prevEl: '.swiper-button-prev3',
        },
        centeredSlides: false, // 슬라이드를 왼쪽 정렬
        watchOverflow: true, // 슬라이드가 부족할 경우 네비게이션 숨김
        breakpoints: {
            0: {
                slidesPerView: 2.2, // 작은 화면에서 2.2개 슬라이드 표시
            },
            768: {
                slidesPerView: 4.2, // 중간 화면에서 4.2개 슬라이드 표시
            },
            1200: {
                slidesPerView: 6.2, // 큰 화면에서 6.2개 슬라이드 표시
            }
        }
    });
});
