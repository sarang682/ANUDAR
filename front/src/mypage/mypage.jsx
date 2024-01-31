import { React, createContext, useState, useRef } from "react";
import MyTab from "../components/mytab/mytab";
import MyInfo from "../components/myinfo/myinfo";
import ChangePassword from "../components/changepassword/changepassword";
import { modalback } from "../navbar/navbar";
import MyHistory from "../components/myinfo/myhistory/myhistory";
import CheckPassword from "../components/checkpassword";

export const MypageContext = createContext();

function Mypage() {

    const [passwordcheck, setPasswordCheck] = useState(true);
    const [passpopup, setPassPopup] = useState(false);
    const mytab = ["내 정보", "찜한 전시회", "찜한 작품", "팔로잉 목록", "경매 내역", "내 전시", "내 작품"];
    const [myindex, setMyIndex] = useState(mytab[0]);

    const scrollspot = {
        "찜한 전시회" : 0,
        "찜한 작품": 1,
        "팔로잉 목록": 2,
        "경매 내역": 3,
        "내 전시": 4,
        "내 작품": 5,
    };
    const scrollref = useRef([]);
    const scrolltoref = (ref) => {
        if (scrollref.current[scrollspot[ref]]) {
            scrollref.current[scrollspot[ref]].scrollIntoView({behavior: "smooth"});
        }
        else {
            window.scrollTo(0, 0);
        }
    }

    return (
        <MypageContext.Provider value={{ passpopup, setPassPopup, mytab, myindex, setMyIndex, scrollref, scrollspot, scrolltoref, passwordcheck, setPasswordCheck }}>
            <div style={{ width: "100%" }}>
                <div style={{ float: "left", width: "25%", zIndex: "1", }}><MyTab /></div>
                <div style={{ float: "right", width: "70%", margin: "30px 0px", }} >{ passwordcheck? <div style={{ position: "absolute", left: "45%" }}><CheckPassword/></div> : (myindex === mytab[0]? <MyInfo /> : <MyHistory />)}</div>
            </div>
            { passpopup? <><div style={modalback} onClick={() => { setPassPopup(false); }}></div><ChangePassword/></> : null }
        </MypageContext.Provider>
    );
}

export default Mypage;