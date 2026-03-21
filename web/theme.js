/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener("DOMContentLoaded",function(){
    const switchBtn=document.getElementById("switch1");
    
    let savedTheme=localStorage.getItem("theme");
    
    if(savedTheme === "dark" ){
        document.documentElement.setAttribute("data-bs-theme","dark");
        if(switchBtn){
            switchBtn.checked=true;
        }
    }
    
    if(switchBtn){
        switchBtn.addEventListener("change",function(){
            if(this.checked){
                document.documentElement.setAttribute("data-bs-theme","dark");
                localStorage.setItem("theme","dark");
            }else{
                document.documentElement.setAttribute("data-bs-theme","light");
                localStorage.setItem("theme", "light");
            }
        });
    }
});
