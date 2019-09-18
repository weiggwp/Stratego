let selected = undefined;
let x = undefined;
let y = undefined;
let moving = -1;
//TODO: Evan is currently working on this file so don't work on it yet
//I made some changes to keep account of the prev_x,prev_y values
function highlight(i,m) {

    let id = (i*10+m).toString();
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
        x = i;
        y = m;

    }
    console.log(selected);
    //target.className = (target.className === "red_front") ? "highlighted" : "red_front";
}
//defines user move -> user should not be able to select blue tiles
//computer moves will be defined in another function
function move(i,m) {


    if (selected!==undefined) {
        //  alert("moving from " + moving + " to " +(i*10+m));
        let tar = document.getElementById((i*10+m).toString());
        let tar_click = "highlight("+i.valueOf()+","+m.valueOf()+")";
        let tar_class = tar.getAttribute("class");

        //let tar = document.getElementById(moving.toString());
        let src=selected;
        let src_click = "move("+x.valueOf()+","+y.valueOf()+")";
        let src_class = src.getAttribute("class");


        tar.src = src.src;

        src.setAttribute('onclick',src_click);
        tar.setAttribute('onclick',tar_click);
        src.setAttribute("class",tar_class);
        tar.setAttribute("class",src_class);

        //src.onClick = tar_click;
        //tar.onClick = src_click;

        src.style.opacity=".02";
        src.style.borderStyle='none';


        //let user_class = tar.className;
        //src.src=tar.src;

        tar.style.opacity="1";
        tar.classList.remove("highlighted");

        moving=-1;
        selected=undefined;
    }
}