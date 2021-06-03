<template>
    <div><h1>ERROR USUARIO NO VALIDO</h1></div> 
</template>
<script>
export default {
    props: ['loginData'],
    mounted(){
        
        if(this.loginData === undefined || this.loginData.isLogged === false){
            this.$router.push({name:'Login'});
        }else{
             if(this.loginData.tipoUser === 'ADMIN' || this.loginData.tipoUser === 'SUPERADMIN' ){
              this.$router.push({name:'VistaAdmin'})
            }else if(this.loginData.tipoUser === 'MEDICO'){ 
              this.$router.push({name:'VistaMedico'})
            }else{// if(this.loginData.tipoUser === 'PACIENTE')
                this.$router.push({name:'VistaPaciente'})
            }
        }
        
    }
}
</script>