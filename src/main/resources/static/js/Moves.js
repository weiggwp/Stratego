let selected = undefined;
<<<<<<< HEAD
let moving = -1;
=======

>>>>>>> 11efd43e7c196492252151482d1b78d8006c569a


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
<<<<<<< HEAD
function move(i,m) {
    if (moving==-1){
        moving=(i*10+m);
        highlight(moving);

        //alert(moving);
        return;
    }

    if (moving>=0) {
        //  alert("moving from " + moving + " to " +(i*10+m));
        document.getElementById((i*10+m).toString()).src=document.getElementById(moving.toString()).src
        document.getElementById(moving.toString()).style.opacity=".02";
        document.getElementById(moving.toString()).style.borderStyle='none';
        document.getElementById((i*10+m).toString()).style.opacity="1";
        moving=-1
    }
}
=======
>>>>>>> 11efd43e7c196492252151482d1b78d8006c569a
