const url = 'http://localhost:8080/ReimbursementApplication/';


let usern = "";
let userp = "";

let loggedIn = false;
let employeeType = "";
let loginFormDiv = document.getElementById('login_form');
let loginFormContent = loginFormDiv.innerHTML;

document.getElementById("loginbtn").addEventListener('click', loginFunc);


async function loginFunc() {
    usern = document.getElementById("username").value;
    userp = document.getElementById("password").value;
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
    
 
    if (responseRole.includes("Employee")) {
        employeeType = "Employee";
        loggedIn = true;
    }
    
    if (responseRole.includes("Manager")) {
        employeeType = "Manager";
        loggedIn = true;
    }
    

    if (loggedIn && employeeType == "Employee") {
        employeeMenu();
    }
    if (loggedIn && employeeType == "Manager") {
        managerMenu();
    }
    
   
}


function employeeMenu() {
    
    //document.getElementById('login_form').innerText = "";
    loginFormDiv.innerText = "";
    const employeeDiv = document.createElement("div");

    //Setting up header of menu
    const employeeHeader = document.createElement("h1");
    employeeHeader.id = "employee_header";
    employeeHeader.classList.add("text-center");
    const headerText = document.createTextNode("Welcome to the Employee Menu!");
    employeeHeader.appendChild(headerText);
    employeeDiv.appendChild(employeeHeader);

    //setting up buttons in menu
    const emplButtonDiv = document.createElement("div");
    emplButtonDiv.id = "employee_buttons";
    const addRequest = document.createElement("button");

    addRequest.id = "request_button";
    const requestsText = document.createTextNode("Add Reimbursement Request");
    addRequest.appendChild(requestsText);
    addRequest.classList.add("btn", "btn-success");

    addRequest.onclick = async function() {

        emplButtonDiv.innerText = "";
        employeeHeader.innerText = "";

        //sets up request form
        const requestForm = document.createElement("div");
        requestForm.id = "request_form";
        //requestForm.classList.add("required", "two", "narrow", "field");

        const requestHeader = document.createElement("h3");
        requestHeader.id = "request_header";
        requestHeader.classList.add("text-center");
        const reqHeaderText = document.createTextNode("Reimbursement Request Form");
        requestHeader.appendChild(reqHeaderText);
        requestForm.appendChild(requestHeader);
    
       

        
        const labelDescription = document.createElement("label");
        labelDescription.htmlFor = "reimb_description";
        const labelDescriptionText = document.createTextNode("Please describe the reimbursement: ");
        labelDescription.appendChild(labelDescriptionText);
        const reimbDescription = document.createElement("textarea");
        reimbDescription.id = "reimb_description";
        reimbDescription.name = "reimb_description";
        reimbDescription.rows = 3;
        reimbDescription.cols = 50;
        requestForm.appendChild(labelDescription);
        requestForm.appendChild(reimbDescription);

        const br = document.createElement("br");
        const br2 = document.createElement("br");
        requestForm.appendChild(br);
        requestForm.appendChild(br2);

        const labelAmount = document.createElement("label");
        labelAmount.htmlFor = "request_amount";
        const labelAmountText = document.createTextNode("Enter Reimbursement Amount: ");
        labelAmount.appendChild(labelAmountText);
        const requestAmount = document.createElement("input");
        requestAmount.id = "request_amount";
        requestAmount.name= "request_amount";
        requestAmount.type = "text";
        requestForm.appendChild(labelAmount);
        requestForm.appendChild(requestAmount);

        const br3 = document.createElement("br");
        const br4 = document.createElement("br");
        requestForm.appendChild(br3);
        requestForm.appendChild(br4);

        const radioP = document.createElement("p");
        const radioPText = document.createTextNode("Select the reimbursement type: ");
        radioP.appendChild(radioPText);
        requestForm.appendChild(radioP);

        const lodgingLabel = document.createElement("label");
        lodgingLabel.htmlFor = "lodging";
        const lodgingLabelText = document.createTextNode("Lodging ");
        lodgingLabel.appendChild(lodgingLabelText);
        const lodging = document.createElement("input");
        lodging.id = "lodging";
        lodging.value = "lodging";
        lodging.name = "reimb_type";
        lodging.type = "radio";
        requestForm.appendChild(lodging);
        requestForm.appendChild(lodgingLabel);
        
        const travelLabel = document.createElement("label");
        travelLabel.htmlFor = "travel";
        const travelLabelText = document.createTextNode("Travel");
        travelLabel.appendChild(travelLabelText);
        const travel= document.createElement("input");
        travel.id = "travel";
        travel.value = "travel";
        travel.name = "reimb_type";
        travel.type = "radio";
        requestForm.appendChild(travel);
        requestForm.appendChild(travelLabel);

        const foodLabel = document.createElement("label");
        foodLabel.htmlFor = "food";
        const foodLabelText = document.createTextNode("Food ");
        foodLabel.appendChild(foodLabelText);
        const food = document.createElement("input");
        food.id = "food";
        food.value = "food";
        food.name = "reimb_type";
        food.type = "radio";
        requestForm.appendChild(food);
        requestForm.appendChild(foodLabel);

        const otherLabel = document.createElement("label");
        otherLabel.htmlFor = "other";
        const otherLabelText = document.createTextNode("Other ");
        otherLabel.appendChild(otherLabelText);
        const other = document.createElement("input");
        other.id = "other";
        other.value = "other";
        other.name = "reimb_type";
        other.type = "radio";
        requestForm.appendChild(other);
        requestForm.appendChild(otherLabel);
        
        const br5 = document.createElement("br");
        const br6 = document.createElement("br");
        requestForm.appendChild(br5);
        requestForm.appendChild(br6);

        const submit = document.createElement("input");
        submit.type = "submit";
        submit.value = "Submit";
        submit.classList.add("btn", "btn-success");
        requestForm.appendChild(submit);

        document.body.appendChild(requestForm);

        
       // <input type = "text" id="request_amount" name = "request_amount"></input>

       submit.onclick = async function() {

            //gets radio button's type
            let type = document.querySelector('input[name="reimb_type"]:checked').value;

            let reimbRequest = {
                username:usern,
                description:reimbDescription.value,
                amount:requestAmount.value,
                type:type
            };
            
            let reimbResp = await fetch(url+'request', {
                method:"POST",
                body: JSON.stringify(reimbRequest),
                credentials:'include'
                //Credentials:include will ensure that the cookie is captured, being useful for later
                //fetch requests
            });

            
            let requestResp = await reimbResp.json();


            if (requestResp=="Description too long!") {

                let warningExists = document.getElementById("description_warning");

                if (!warningExists) {
                    const descriptionWarning = document.createElement("p");
                    descriptionWarning.id = "description_warning";
                    descriptionWarningText = document.createTextNode("Description is too long! Please limit to under 250 characters.");
                    descriptionWarning.appendChild(descriptionWarningText);
                    requestForm.appendChild(descriptionWarning);
                    labelDescription.classList.add("red");
                }
            }

            if (requestResp=="Please enter a number for reimbursement amount!") {

                let warningExists = document.getElementById("non_numeric_warning");

                if (!warningExists) {
                    const amountWarning = document.createElement("p");
                    amountWarning.id = "non_numeric_warning";
                    const amountWarningText = document.createTextNode("Please enter a number for reimbursement amount!");
                    amountWarning.appendChild(amountWarningText);
                    requestForm.appendChild(amountWarning);
                    labelAmount.classList.add("red");
                }  
                
            }

            if (requestResp=="Please enter a positive amount!") {
                
                let warningExists = document.getElementById("amount_warning");

                if (!warningExists) {
                    const amountWarning = document.createElement("p");
                    amountWarning.id = "amount_warning";
                    amountWarningText = document.createTextNode("Please enter a positive amount.");
                    amountWarning.appendChild(amountWarningText);
                    
                        requestForm.appendChild(amountWarning);
                        labelAmount.classList.add("red");
                }
                
            }

            if (requestResp=="Request Sent!") {
                requestForm.innerText = "";
                const sentHeader = document.createElement("h3");
                const sentHeaderText = document.createTextNode("Reimbursement Request Sent!");
                sentHeader.id = "sent_header";
                sentHeader.appendChild(sentHeaderText);

                const goBackButton = document.createElement("button");
                goBackButton.id = "go_back_button";
                goBackButton.classList.add("btn", "btn-success");
                const goBackButtonText = document.createTextNode("Go Back to Employee Menu");
                goBackButton.appendChild(goBackButtonText);
                goBackButton.onclick = function() {
                    sentHeader.innerText = "";
                    goBackButton.style.display = "none";
                    employeeMenu();
                }
                
                document.body.appendChild(sentHeader);
                document.body.appendChild(goBackButton);

                
            }
            
           
       }
        
       
    
    }


  
    const viewTickets = document.createElement("button");
    viewTickets.id = "tickets_button";
    const ticketsText = document.createTextNode("View Past Tickets");
    viewTickets.appendChild(ticketsText);
    viewTickets.classList.add("btn", "btn-success");

    viewTickets.onclick = async function() {

        let ticketRequest = {
            username:usern,
        };

        let ticketResp = await fetch(url+'tickets', {
            method:"POST",
            body: JSON.stringify(ticketRequest),
            credentials:'include'
            //Credentials:include will ensure that the cookie is captured, being useful for later
            //fetch requests
        });

        let ticketData = await ticketResp.json();


        emplButtonDiv.innerText = "";
        employeeHeader.innerText = "";

        const ticketTable = document.createElement("table");
        ticketTable.id = "ticket_table";
        ticketTable.classList.add("table", "table-hovered", "table-bordered");
        const ticketHead = document.createElement("thead");
        const ticketHeadRow = document.createElement("tr");
        const submittedDate = document.createElement("th");
        const submittedDateText = document.createTextNode("Submitted Date");
        submittedDate.appendChild(submittedDateText);
        const amount = document.createElement("th");
        const amountText = document.createTextNode("Amount");
        amount.appendChild(amountText);
        const description = document.createElement("th");
        const descriptionText = document.createTextNode("Description");
        description.appendChild(descriptionText);
        const status = document.createElement("th");
        const statusText = document.createTextNode("Status");
        status.appendChild(statusText);

        ticketHeadRow.appendChild(submittedDate);
        ticketHeadRow.appendChild(amount);
        ticketHeadRow.appendChild(description);
        ticketHeadRow.appendChild(status);
        ticketHead.appendChild(ticketHeadRow);
        ticketTable.appendChild(ticketHead);

        const ticketBody = document.createElement("tbody");
        ticketTable.appendChild(ticketBody);
        
        document.body.appendChild(ticketTable);

        for (let ticket of ticketData) {
            let row = document.createElement("tr");

            let cell1 = document.createElement("td");
            let readableTime = new Date(ticket.submitDate).toString();
            cell1.innerHTML = readableTime;
            
            row.appendChild(cell1);
    
            let cell2 = document.createElement("td");
            cell2.innerHTML = ticket.amount;
            row.appendChild(cell2);
    
            let cell3 = document.createElement("td");
            cell3.innerHTML = ticket.description;
            row.appendChild(cell3);
    
            let cell4 = document.createElement("td");
            cell4.innerHTML = ticket.status;
            row.appendChild(cell4);
            
            ticketBody.appendChild(row);
        }

        const goBackButton = document.createElement("button");
        goBackButton.id = "go_back_button";
        goBackButton.classList.add("btn", "btn-success");
        const goBackButtonText = document.createTextNode("Go Back to Employee Menu");
        goBackButton.appendChild(goBackButtonText);
        goBackButton.onclick = function() {
            ticketTable.remove();
            goBackButton.style.display = "none";
            employeeMenu();
        }
        document.body.appendChild(goBackButton);
    }
    const logOut = document.createElement("button");
    logOut.id = "log_out_button";
    const logOutText = document.createTextNode("Log Out");
    logOut.appendChild(logOutText);
    logOut.classList.add("btn", "btn-danger");

    logOut.onclick = function () {
        employeeDiv.innerHTML = "";
        loginFormDiv.innerHTML = loginFormContent; 
        document.getElementById("loginbtn").addEventListener('click', loginFunc);
    }

    emplButtonDiv.appendChild(addRequest);
    emplButtonDiv.appendChild(viewTickets);
    emplButtonDiv.appendChild(logOut);

    employeeDiv.appendChild(emplButtonDiv);
    document.body.appendChild(employeeDiv);
    

}

function managerMenu() {

     
    //document.getElementById('login_form').innerText = "";
    loginFormDiv.innerText = "";
    const managerDiv = document.createElement("div");

    //Setting up header of menu
    const managerHeader = document.createElement("h1");
    managerHeader.id = "manager_header";
    managerHeader.classList.add("text-center");
    const headerText = document.createTextNode("Welcome to the Manager Menu!");
    managerHeader.appendChild(headerText);
    managerDiv.appendChild( managerHeader);

    
    //setting up buttons in menu
    const managerButtonDiv = document.createElement("div");
    managerButtonDiv.id = "manager_buttons";

    const viewReimbursements = document.createElement("button");
    viewReimbursements.id = "reimbursement_button";
    const viewReimbText = document.createTextNode("View Reimbursements");
    viewReimbursements.appendChild(viewReimbText);
    viewReimbursements.classList.add("btn", "btn-success");

    managerButtonDiv.onclick = async function viewReimbursementTable() {

        let managerRequest = {
            username:usern,
        };

        let managerResp = await fetch(url+'reimbursements', {
            method:"POST",
            body: JSON.stringify(managerRequest),
            credentials:'include'
            //Credentials:include will ensure that the cookie is captured, being useful for later
            //fetch requests
        });
        
        managerHeader.remove();
        managerButtonDiv.remove();

        let reimbursementData = await managerResp.json();

        const pendingOnly = document.createElement("button");
        pendingOnly.id = "pending_button";
        const pendingOnlyText = document.createTextNode("View Only Pending");
        pendingOnly.appendChild(pendingOnlyText);
        pendingOnly.classList.add("btn", "btn-success");
        document.body.appendChild(pendingOnly);


        pendingOnly.onclick = async function() {
            console.log("test23");
            pendingOnly.remove();
            reimbursementTable.remove();
            statusForm.remove();

            let pendingRequest = {
                username:usern,
            };
    
            let pendingResp = await fetch(url+'pending', {
                method:"POST",
                body: JSON.stringify(pendingRequest),
                credentials:'include'
                //Credentials:include will ensure that the cookie is captured, being useful for later
                //fetch requests
            });
            
            let pendingData = await pendingResp.json();
            		
            const pendingTable = document.createElement("table");
            pendingTable.id = "pending_table";
            pendingTable.classList.add("table", "table-hovered", "table-bordered");
            const pendingHead = document.createElement("thead");
            const pendingHeadRow = document.createElement("tr");
            const statusId = document.createElement("th");
            const statusIdText = document.createTextNode("Status ID");
            statusId.appendChild(statusIdText);
            const author = document.createElement("th");
            const authorText = document.createTextNode("Author");
            author.appendChild(authorText);
            const submittedDate = document.createElement("th");
            const submittedDateText = document.createTextNode("Submitted Date");
            submittedDate.appendChild(submittedDateText);
            const amount = document.createElement("th");
            const amountText = document.createTextNode("Amount");
            amount.appendChild(amountText);
            const description = document.createElement("th");
            const descriptionText = document.createTextNode("Description");
            description.appendChild(descriptionText);
            const status = document.createElement("th");
            const statusText = document.createTextNode("Status");
            status.appendChild(statusText);
            
            pendingHeadRow.appendChild(statusId);
            pendingHeadRow.appendChild(author);
            pendingHeadRow.appendChild(submittedDate);
            pendingHeadRow.appendChild(amount);
            pendingHeadRow.appendChild(description);
            pendingHeadRow.appendChild(status);
            pendingHead.appendChild(pendingHeadRow);
            pendingTable.appendChild(pendingHead);
            
            const pendingBody = document.createElement("tbody");
            pendingTable.appendChild(pendingBody);
            

            
            for (let pending of pendingData) {
                
                let row = document.createElement("tr");
            
                let cell1 = document.createElement("td");
                cell1.innerHTML = pending.statusId;
                row.appendChild(cell1);
                
                let cell2 = document.createElement("td");
                cell2.innerHTML = pending.author;
                row.appendChild(cell2);
            
                let cell3 = document.createElement("td");
                let readableTime = new Date(pending.submitDate).toString();
                cell3.innerHTML = readableTime;
                row.appendChild(cell3);
            
                let cell4 = document.createElement("td");
                cell4.innerHTML = pending.amount;
                row.appendChild(cell4);
            
                let cell5 = document.createElement("td");
                cell5.innerHTML = pending.description;
                row.appendChild(cell5);
            
                let cell6 = document.createElement("td");
                cell6.innerHTML = pending.status;
                row.appendChild(cell6);
            
                pendingBody.appendChild(row);
            }

            pendingTable.appendChild(pendingBody);
            document.body.appendChild(pendingTable);

            const goBackToAllButton = document.createElement("button");
                goBackToAllButton.id = "go_back_to_all_button";
                goBackToAllButton.classList.add("btn", "btn-success");
                const goBackToAllButtonText = document.createTextNode("Go Back to Viewing All Reimbursements");
                goBackToAllButton.appendChild(goBackToAllButtonText);

                goBackToAllButton.onclick = function() {
                    statusHeader.remove();
                    reimbursementTable.remove();
                    pendingTable.remove();
                    statusForm.remove();
                    goBackToAllButton.remove();
                    viewReimbursementTable();
                }

            logOut2.onclick = function () {
                    pendingOnly.remove();
                    pendingTable.remove();
                    statusHeader.remove();
                    statusForm.remove();
                    goBackToAllButton.remove();
                    logOut2.remove();
                    loginFormDiv.innerHTML = loginFormContent; 
                    document.getElementById("loginbtn").addEventListener('click', loginFunc);
                }
            document.body.appendChild(goBackToAllButton);

        }
            
        const reimbursementTable = document.createElement("table");
        reimbursementTable.id = "reimbursement_table";
        reimbursementTable.classList.add("table", "table-hovered", "table-bordered");
        const reimbursementHead = document.createElement("thead");
        const reimbursementHeadRow = document.createElement("tr");
        const statusId = document.createElement("th");
        const statusIdText = document.createTextNode("Status ID");
        statusId.appendChild(statusIdText);
        const author = document.createElement("th");
        const authorText = document.createTextNode("Author");
        author.appendChild(authorText);
        const submittedDate = document.createElement("th");
        const submittedDateText = document.createTextNode("Submitted Date");
        submittedDate.appendChild(submittedDateText);
        const amount = document.createElement("th");
        const amountText = document.createTextNode("Amount");
        amount.appendChild(amountText);
        const description = document.createElement("th");
        const descriptionText = document.createTextNode("Description");
        description.appendChild(descriptionText);
        const status = document.createElement("th");
        const statusText = document.createTextNode("Status");
        status.appendChild(statusText);

        reimbursementHeadRow.appendChild(statusId);
        reimbursementHeadRow.appendChild(author);
        reimbursementHeadRow.appendChild(submittedDate);
        reimbursementHeadRow.appendChild(amount);
        reimbursementHeadRow.appendChild(description);
        reimbursementHeadRow.appendChild(status);
        reimbursementHead.appendChild(reimbursementHeadRow);
        reimbursementTable.appendChild(reimbursementHead);

        reimbursementBody = document.createElement("tbody");
        reimbursementTable.appendChild(reimbursementBody);
        
        reimbursementBody = document.createElement("tbody");
        reimbursementTable.appendChild(reimbursementBody)
            

        for (let reimbursement of reimbursementData) {
            
            let row = document.createElement("tr");

            let cell1 = document.createElement("td");
            cell1.innerHTML = reimbursement.statusId;
            row.appendChild(cell1);
            
            let cell2 = document.createElement("td");
            cell2.innerHTML = reimbursement.author;
            row.appendChild(cell2);

            let cell3 = document.createElement("td");
            let readableTime = new Date(reimbursement.submitDate).toString();
            cell3.innerHTML = readableTime;
            row.appendChild(cell3);
    
            let cell4 = document.createElement("td");
            cell4.innerHTML = reimbursement.amount;
            row.appendChild(cell4);
    
            let cell5 = document.createElement("td");
            cell5.innerHTML = reimbursement.description;
            row.appendChild(cell5);
    
            let cell6 = document.createElement("td");
            cell6.innerHTML = reimbursement.status;
            row.appendChild(cell6);
    
            reimbursementBody.appendChild(row);
            
        }

        reimbursementTable.appendChild(reimbursementBody);
        document.body.appendChild(reimbursementTable);


        const statusForm = document.createElement("div");
        statusForm.id = "status_form";

        const statusHeader = document.createElement("h3");
        statusHeader.id = "status_header";
        const statusHeaderText = document.createTextNode("Change Status Form");
        statusHeader.appendChild(statusHeaderText);
        document.body.appendChild(statusHeader);
        statusForm.appendChild(statusHeader);

        const labelId = document.createElement("label");
        labelId.htmlFor = "id_input";
        const labelIdText = document.createTextNode("Enter Status Id: ");
        labelId.appendChild(labelIdText);
        const inputId = document.createElement("input");
        inputId.id = "id_input";
        inputId.name = "id_input";
        inputId.type = "text";
        statusForm.appendChild(labelId);
        statusForm.appendChild(inputId);

        const br = document.createElement("br");
        const br2 = document.createElement("br");
        statusForm.appendChild(br);
        statusForm.appendChild(br2);

        const approvedLabel = document.createElement("label");
        approvedLabel.htmlFor = "approved";
        const approvedLabelText = document.createTextNode("Approve");
        approvedLabel.appendChild(approvedLabelText);
        const approved = document.createElement("input");
        approved.id = "approved";
        approved.value = "approved";
        approved.name = "status_type";
        approved.type = "radio";
        statusForm.appendChild(approvedLabel);
        statusForm.appendChild(approved);

        const deniedLabel = document.createElement("label");
        deniedLabel.htmlFor = "denied";
        const deniedLabelText = document.createTextNode("Deny");
        deniedLabel.appendChild(deniedLabelText);
        const denied = document.createElement("input");
        denied.id = "denied";
        denied.value = "denied";
        denied.name = "status_type";
        denied.type = "radio";
        statusForm.appendChild(deniedLabel);
        statusForm.appendChild(denied);

        const br3 = document.createElement("br");
        const br4 = document.createElement("br");
        statusForm.appendChild(br3);
        statusForm.appendChild(br4);

        const submitStatus = document.createElement("input");
        submitStatus.type = "submit";
        submitStatus.value = "Submit";
        submitStatus.id = "submit_status";
        submitStatus.classList.add("btn", "btn-success");
        statusForm.appendChild(submitStatus);

    
        reimbursementTable.appendChild(reimbursementBody);
        document.body.appendChild(reimbursementTable);

        

        submitStatus.onclick = async function() {
            
            //gets radio button's type
            let checkedStatus = document.querySelector('input[name="status_type"]:checked').value;
            
            let statusRequest = {

                username:usern,
                statusId: inputId.value,
                status: checkedStatus
            };
        
            
            let statusResp = await fetch(url+'statusChange', {
                method:"POST",
                body: JSON.stringify(statusRequest),
                credentials:'include'
                //Credentials:include will ensure that the cookie is captured, being useful for later
                //fetch requests
            });

            
            let statusData = await statusResp.json();

            if (statusData =="ID not found") {

                let warningExists = document.getElementById("id_warning");

                if (!warningExists) {
                    const idWarning = document.createElement("p");
                    idWarning.id = "id_warning";
                    const idWarningText = document.createTextNode("No ID found for ");
                    idWarning.appendChild(idWarningText);
                    statusForm.appendChild(idWarning);
                    labelId.classList.add("red");
                }
            }
            if (statusData=="Status Changed!") {
                pendingOnly.remove();
                reimbursementTable.remove();
                statusHeader.remove();
                statusForm.remove();
                viewReimbursementTable();
            }

        }

        document.body.appendChild(statusForm);    
        
        const logOut2 = document.createElement("button");
        logOut2.id = "manager_log_out_button2";
        const logOutText2 = document.createTextNode("Log Out");
        logOut2.appendChild(logOutText2);
        logOut2.classList.add("btn", "btn-danger");
        
        logOut2.onclick = function () {
            pendingOnly.remove();
            reimbursementTable.remove();
            statusHeader.remove();
            statusForm.remove();
            logOut2.remove();
            loginFormDiv.innerHTML = loginFormContent; 
            document.getElementById("loginbtn").addEventListener('click', loginFunc);
        }
        document.body.appendChild(logOut2);
    }

    const logOut = document.createElement("button");
    logOut.id = "manager_log_out_button";
    const logOutText = document.createTextNode("Log Out");
    logOut.appendChild(logOutText);
    logOut.classList.add("btn", "btn-danger");

    logOut.onclick = function () {
        managerDiv.innerHTML = "";
        loginFormDiv.innerHTML = loginFormContent; 
        document.getElementById("loginbtn").addEventListener('click', loginFunc);
    }
    managerButtonDiv.appendChild(viewReimbursements);
    managerButtonDiv.appendChild(logOut);
    managerDiv.appendChild(managerButtonDiv);
    document.body.appendChild(managerDiv);

}
