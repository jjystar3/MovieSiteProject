window.addEventListener("scroll", function() {
  const header = document.getElementById("header");

  // 스크롤 위치가 50px 이상이면 배경색을 #060606으로 변경
  if (window.scrollY > 50) {
      header.style.backgroundColor = "#060606";
  } else {
      // 스크롤이 50px 이하이면 다시 투명으로 변경
      header.style.backgroundColor = "transparent";
  }
});