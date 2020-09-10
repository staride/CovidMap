<template>
  <v-main class="py-12">
    <v-container class="fill-height" fluid>
      <v-row align="center" justify="center">
        <v-col cols="12" sm="11" class="pa-0">
          <v-card>
            <v-card-title>
              <v-spacer/>
              COVID PATH MAP
              <v-spacer/>
              <div class="ma-0 pa-0" id="selectDiv">
                <v-select class="ma-0 pa-0" v-model="selectItem" :items="items" @change="changeRadius" :hide-details="true"/>
              </div>
              <div class="my-0 mr-0 ml-2 pa-0" id="searchDiv">
                <v-text-field class="ma-0 pa-0" label="장소 입력" v-model="searchText" type="text" single-line />
              </div>
              <div class="my-0 mr-0 ml-2 pa-0" id="searchBtnDiv">
                <v-btn class="ma-0 pa-0" text color="black" @click="searchPlace()">검색</v-btn>
              </div>
            </v-card-title>
            <v-card-text>
              <div id="map" style="height: 520px"></div>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>

<script>

import postscribe from 'postscribe'
import { mapGetters } from 'vuex'
import axios from 'axios'
import {
  SET_LOGIN_LOACTION_XY
} from '@/store/mutation-types.js'

export default {
  name: 'Index',
  data: function () {
    return {
      selectItem: 100,
      items: [
        { text: '100m', value: 100 },
        { text: '200m', value: 200 },
        { text: '300m', value: 300 }
      ],
      map: null,
      markers: [],
      centerMarker: null,
      circle: null,
      searchText: ''
    }
  },
  computed: {
    ...mapGetters([
      'getLoginLocationX',
      'getLoginLocationY',
      'getLoginId'
    ])
  },
  methods: {
    initScript: function () {
      // console.log('initScript')
      let src = '<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=27182d803bc31145e52687dda5546c30&libraries=services"></scrip'
      src = src + 't>'

      const initFunction = this.initMap

      postscribe(document.head, src, {
        done: initFunction
      })
    },
    initMap: function () {
      // console.log('initMap')
      this.getMap(this.getNewDefaultPosition())
      this.getCenterMarker()
      this.getCircle()
      this.getMarkersFromAddress()
    },
    getMap: function (position) {
      let map

      if (this.map === null) {
        const container = document.getElementById('map')
        const options = {
          center: position,
          level: 3
        }

        map = new window.kakao.maps.Map(container, options)
        this.map = map
      } else {
        map = this.map
      }

      return map
    },
    getNewDefaultPosition: function () {
      return new window.kakao.maps.LatLng(this.getLoginLocationX, this.getLoginLocationY)
    },
    getCenterMarker: function () {
      let center

      if (this.centerMarker === null) {
        center = new window.kakao.maps.Marker({
          map: this.map,
          position: this.getNewDefaultPosition()
        })

        this.centerMarker = center
      } else {
        center = this.centerMarker
      }

      return center
    },
    getNewMarker: function (position) {
      const marker = new window.kakao.maps.Marker({
        map: this.map,
        position: position
      })

      return marker
    },
    getCircle: function () {
      let circle = null

      if (this.circle === null) {
        circle = new window.kakao.maps.Circle({
          center: this.getNewDefaultPosition(),
          radius: this.selectItem,
          strokeWeight: 2,
          strokeColor: '#FF0000',
          strokeOpacity: 0.8,
          strokeStyle: 'dashed',
          fillColor: '#00ECF4',
          fillOpacity: 0.1
        })

        circle.setMap(this.map)
        this.circle = circle
      } else {
        circle = this.circle
      }

      return circle
    },
    getMarkersFromAddress: function () {
      const geocoder = new window.kakao.maps.services.Geocoder()
      const position = this.centerMarker.getPosition()
      const setMarker = this.setMarker
      geocoder.coord2Address(position.getLng(), position.getLat(), function (result, status) {
        if (status === window.kakao.maps.services.Status.OK) {
          const address = result[0].address
          if (address.region_1depth_name === '서울') {
            const type = address.region_2depth_name
            setMarker(type)
          }
        }
      })
    },
    removeMarkers: function () {
      const len = this.markers.length
      if (len > 0) {
        for (let i = 0; i < len; i++) {
          const marker = this.markers[i]
          marker.setMap(null)
        }

        this.markers = []
      }
    },
    setMarker: function (type) {
      axios.get(`http://localhost:7777/craw/${type}`).then(res => {
        const len = res.data.length
        const geocoder = new window.kakao.maps.services.Geocoder()
        const markers = this.markers
        const setViewMarker = this.setViewMarker
        const getMarker = this.getNewMarker
        this.removeMarkers()

        for (let i = 0; i < len; i++) {
          const data = res.data[i]
          // console.log('name : ' + data.locationName)
          geocoder.addressSearch(data.address, function (result, status) {
            if (status === window.kakao.maps.services.Status.OK) {
              const position = new window.kakao.maps.LatLng(result[0].y, result[0].x)
              const marker = getMarker(position)

              marker.setVisible(false)
              setViewMarker(marker)
              markers.push(marker)
            }
          })
        }
      }).catch(err => {
        console.log(err)
      })
    },
    setViewMarker: function (marker) {
      const center = this.circle.getPosition()
      const radius = this.circle.getRadius()
      const line = new window.kakao.maps.Polyline()
      const path = [marker.getPosition(), center]
      line.setPath(path)

      const dist = line.getLength()
      // console.log('radius : ' + radius)
      // console.log('dist : ' + dist)

      if (dist <= radius) {
        marker.setVisible(true)
      } else {
        marker.setVisible(false)
      }
    },
    changeRadius: function () {
      const len = this.markers.length
      this.circle.setRadius(this.selectItem)

      for (let i = 0; i < len; i++) {
        const marker = this.markers[i]
        this.setViewMarker(marker)
      }
    },
    setCenterMarkerPosition: function (position) {
      this.centerMarker.setPosition(position)
    },
    setCirclePosition: function (position) {
      this.circle.setPosition(position)
    },
    searchPlace: function () {
      const places = new window.kakao.maps.services.Places()
      const map = this.map

      const setMarker = this.setMarker
      const setCenterMarkerPosition = this.setCenterMarkerPosition
      const setCirclePosition = this.setCirclePosition
      const getLoginId = this.getLoginId

      const store = this.$store

      places.keywordSearch(this.searchText, function (result, status) {
        if (status === window.kakao.maps.services.Status.OK) {
          console.log(result)

          const len = result.length
          let type = null
          let x = -1
          let y = -1

          for (let i = 0; i < len; i++) {
            const address = result[i].road_address_name

            if (address.split(' ')[0] === ('서울')) {
              type = address.split(' ')[0]
              x = result[i].y
              y = result[i].x
              break
            }
          }

          if (type === null) {
            alert('검색범위는 서울내로 한정합니다')
          } else {
            const position = new window.kakao.maps.LatLng(x, y)
            map.panTo(position)

            setCenterMarkerPosition(position)
            setCirclePosition(position)
            setMarker(type)

            const id = getLoginId
            if (id != null) {
              store.commit(SET_LOGIN_LOACTION_XY, { x: x, y: y })
              axios.put(`http://localhost:7777/member/coordinate/${id}`, { x: x, y: y }).then(res => {
                if (res.status === 200) {
                  console.log(res)
                }
              }).catch(err => {
                console.log(err)
              })
            }
          }
        }
      })
    }
  },
  mounted: function () {
    if (!window.kakao || !window.kakao.maps) {
      this.initScript()
    } else {
      this.initMap()
    }
  }
}
</script>
<style>
#selectDiv {
  width: 80px;
  height: 32px;
}
#searchDiv {
  width: 250px;
  height: 32px;
}
#searchBtnDiv {
  width: 50px;
  height: 32px;
}
</style>
