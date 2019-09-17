let selected = undefined;

let moving = -1;
let started=false;
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
function start(){
    started=true;
    document.getElementsByClassName('blank').draggable = false;
    document.getElementById('startBtn').style.visibility="hidden";
    document.getElementById('startText').style.visibility="hidden";
}
function swap(i,m){
    if (document.getElementById((i*10+m).toString()).src.endsWith("/images/pieces/blue_back.png")) return;
    else console.log(document.getElementById((i*10+m).toString()).src);
    if (moving==-1){

        moving=(i*10+m);
        highlight(moving);

        //alert(moving);
        return;
    }

    if (moving>=0) {
        if (moving==i*10+m){
            highlight(moving)
            moving=-1;
            return;
        }
        //  alert("moving from " + moving + " to " +(i*10+m));
        let tempSrc=document.getElementById((i*10+m).toString()).src;
        document.getElementById((i*10+m).toString()).src=document.getElementById(moving.toString()).src
        document.getElementById(moving.toString()).src=tempSrc;
        highlight(moving);
        moving=-1
    }
}
function move(i,m) {
    if (!started){

        swap(i,m);
        return;
    }
    if (moving==-1){
        if (document.getElementById((i*10+m).toString()).src.endsWith("/images/pieces/blue_back.png")) return;
        else console.log(document.getElementById((i*10+m).toString()).src);

        moving=(i*10+m);
        highlight(moving);

        //alert(moving);
        return;
    }

    if (moving>=0) {
        if (moving==i*10+m){
            highlight(moving)
            moving=-1;
            return;
        }
        //  alert("moving from " + moving + " to " +(i*10+m));
        document.getElementById((i*10+m).toString()).src=document.getElementById(moving.toString()).src
        document.getElementById(moving.toString()).style.opacity=".02";
        document.getElementById(moving.toString()).style.borderStyle='none';
        document.getElementById((i*10+m).toString()).style.opacity="1";
        moving=-1
    }
}
