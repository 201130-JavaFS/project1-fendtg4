const url = 'http://localhost:8080/ReimbursementApplication/';


document.getElementById("loginbtn").addEventListener('click', loginFunc);

let loggedIn = false;
let employeeType = "";

async function loginFunc() {
    let usern = document.getElementById("username").value;
    let userp = document.getElementById("password").value;
    let user = {
        username:usern,
        password:userp
    };
    
    let resp = await fetch(url+'login', {
        method:"POST",
        body: JSON.stringify(user),
        credentials:'include'
        //Credentials:include will ensure that the cookie is captured, being useful for later
        //fetch requests
    });
   

    let responseRole = await resp.json();
    
    console.log(responseRole);
    if (responseRole.includes("Employee")) {
        employeeType = "Employee";
        loggedIn = true;
    }
    
    if (responseRole.includes("Manager")) {
        employeeType = "Manager";
        loggedIn = true;
    }
    
    console.log(employeeType);
    if (loggedIn && employeeType == "Employee") {
        employeeMenu();
    }
    if (loggedIn && employeeType == "Manager") {
        document.getElementById('login-row').innerText = "";
        //managerMenu();
    }
    
   
}

function employeeMenu() {
    document.getElementById('login-row').innerText = "";
    const employeeDiv = document.createElement("div");
    const employeeHeader = document.createElement("h1");
    employeeHeader.id = "employeeHeader";
    employeeHeader.classList.add("text-center");
    const headerText = document.createTextNode("Welcome to the Employee Menu!");
    employeeHeader.appendChild(headerText);
    employeeDiv.appendChild(employeeHeader);
    document.body.appendChild(employeeDiv);

    //create the buttons for employee menu functionalities
    //experiment with  let data = await response.json();
}