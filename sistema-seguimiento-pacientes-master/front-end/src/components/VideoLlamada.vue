<template>
  <div id="session" @error="errorHandler">
    <b-button v-if="enLlamada" @click="disconnect">Salir de la llamada</b-button>
    <b-button v-if="enLlamada" @click="apagarCamara">{{text}}</b-button>

        <div id="subscribers" v-for="stream in streams" :key="stream.streamId">
          <subscriber @error="errorHandler" :stream="stream" :session="session" :opts="options"></subscriber>
        </div>

    <publisher :session="session" @error="errorHandler" ref="publisher"></publisher>
    
   
  </div>
</template>

<script>
import OT from '@opentok/client';
import { EventBus } from '@/eventbus/eventbus.js';
import Publisher from '@/components/Publisher.vue';
import Subscriber from '@/components/Subscriber.vue';
const errorHandler = err => {
  alert(err.message);
};
export default {
  name: 'session',
  components: { Publisher, Subscriber },
  props: {
    apiKey: {
      type: Number,
    },
    sessionId: {
      type: String,
    },
    token: {
      type: String,
    }
  },
  created() {
    this.session = OT.initSession(this.apiKey, this.sessionId);
    this.session.connect(this.token, err => {
      if (err) {
        errorHandler(err);
      }
    });
    this.session.on('streamCreated', event => {
      this.streams.push(event.stream);
    });
    this.session.on('streamDestroyed', event => {
      const idx = this.streams.indexOf(event.stream);
      if (idx > -1) {
        this.streams.splice(idx, 1);
      }
    });
  },
  data: () => ({
    streams: [],
    session: null,
    enLlamada : true,
    options:{width: '100%', height: '100%',  insertMode: 'append'},
    camera: true,
    text: "Apagar camara"
  }),
  methods: {
    errorHandler,
    disconnect(){
      if(this.enLlamada){
          this.session.disconnect();
          this.enLlamada = false
          EventBus.$emit('desconectado',this.session)
      }
      
    },
    apagarCamara(){
      this.camera = !this.camera
      this.$refs.publisher.toglevideo(this.camera)
      if(!this.camera){
          this.text = "Encender camara"
      }else{
         this.text = "Apagar camara"
      }
      
    }
  }
};
</script>