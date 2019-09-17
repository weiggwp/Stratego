let selected = undefined;



function highlight(id) {


    let cur_image = document.getElementById(id);
    let dimensions = cur_image.getBoundingClientRect();
    console.log(dimensions);
    //current_image.classList.remove("highlighted");
    if(selected!==undefined && selected!==cur_image)
    {
        selected.classList.remove("highlighted");
    }

    cur_image.classList.toggle("highlighted");

    if(selected===cur_image)
    {
        selected=undefined;
    }
    else{
        selected=cur_image;
    }
    console.log(selected);
    //target.className = (target.className === "red_front") ? "highlighted" : "red_front";
}
