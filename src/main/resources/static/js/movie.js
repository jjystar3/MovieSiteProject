document.addEventListener("DOMContentLoaded", function() {
	const textSections = [
		{ selector: ".actors", btnSelector: ".actors-btn", maxLength: 100 },
		{ selector: ".synopsis", btnSelector: ".synopsis-btn", maxLength: 120 }
	];

	textSections.forEach(section => {
		const paragraph = document.querySelector(section.selector);
		const showMoreBtn = document.querySelector(section.btnSelector);
		const fullText = paragraph.innerText;
		const truncatedText = fullText.slice(0, section.maxLength) + "...";

		let isTruncated = true; 

		// 초기 텍스트 설정
		if (fullText.length > section.maxLength) {
			paragraph.innerText = truncatedText;
		} else {
			showMoreBtn.style.display = "none";
		}

		// 더보기/접기 버튼 클릭 이벤트
		showMoreBtn.addEventListener("click", function() {
			if (isTruncated) {
				paragraph.innerText = fullText; 
				showMoreBtn.innerText = "접기";
			} else {
				paragraph.innerText = truncatedText;
				showMoreBtn.innerText = "더보기";
			}
			isTruncated = !isTruncated;
		});
	});

	// 스크롤 트리거 이벤트
	gsap.registerPlugin(ScrollTrigger);

	gsap.to(".movie_text-img img", {
		y: -400, 
		ease: "none",
		scrollTrigger: {
			trigger: ".movie_text-img", 
			start: "top bottom", 
			end: "bottom top", 
			scrub: true 
		}
	});
});
