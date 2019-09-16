
function highlight(id) {

    var target = document.getElementById(id);

    //var imgs = document.getElementsByClassName("red_front");

    target.className = (target.className === "red_front") ? "highlighted" : "red_front";
}

//alert("js file loaded");