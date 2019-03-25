let battles;

async function getBattles(){
    await $.ajax(
        {
            type: 'GET',
            url: '/api/battles',
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            success: function(data) {
                console.log(data);
                battles = data;
            }
        });

    await showBattles();
}

async function showBattles(){
    const element = document.getElementById("battles");
    const ul = document.createElement("ul");
    ul.style.listStyle = "none";

    battles.forEach(battle => {
        const div = document.createElement("li");
        div.style.display = "flex";
        div.style.border = "1px solid lightgray";
        div.style.borderRadius = "5px";
        div.style.boxShadow = "1px 1px 10px 1px lightgray";
        div.style.padding = "1em";
        div.style.justifyContent = "space-between";
        div.style.alignItems = "center";

        const p = document.createElement("span");
        const node = document.createTextNode(`Battle ${battle.trainer.name} vs ${battle.opponent.name}`);
        p.appendChild(node);

        const button = document.createElement("a");
        button.classList.add("btn", "btn-primary");
        button.style.color = "white";
        const att = document.createAttribute("href");
        att.value = `/api/battles/${battle.uuid}`;
        button.appendChild(document.createTextNode("See battle"));
        
        const state = document.createElement("span");
        let str = "";
        let color = "green";
        
        switch (battle.battleState) {
            case "STARTING": {
                str = "Starting";
                color = "orange";
                break;
            }
            case "INPROGRESS": {
                str = "In progress";
                color = "green";
                break;
            }
            case "TERMINATE": {
                str = "Terminate";
                color = "red";
                break;
            }

        }

        state.appendChild(document.createTextNode(str));
        state.style.backgroundColor = color;
        state.style.color = "white";
        state.style.padding = "0.5em";
        state.style.borderRadius = "5px";

        div.appendChild(p);
        div.appendChild(state);
        div.appendChild(button);
        ul.appendChild(div);
    });

    element.appendChild(ul);
}
