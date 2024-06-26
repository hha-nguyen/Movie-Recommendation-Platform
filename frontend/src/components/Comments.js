import React, { Component } from "react";
import { BsFillChatQuoteFill } from "react-icons/bs";
import { MdContentCut } from "react-icons/md";

class Comments extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: this.props.id,
      userId: this.props.userId,
    };
    console.log(this.state.id);
    console.log(this.state.userId);
  }

  render() {
    return (
      <div
        className="row"
        style={{
          width: "100%",
          backgroundColor: "#ffffff",
          height: "50px",
          borderRadius: 20,
          margin: 3,
        }}
      >
        <div className="col-10" style={{ color: "black", fontSize: 16 }}>
          {this.props.content}
        </div>
        <div className="col-2">
          {/* In the case of the person who wrote the comment, it can be deleted */}
          {sessionStorage.getItem("user") === this.props.userId.toString() ? (
            <MdContentCut
              onClick={() =>
                this.props.deleteComment(this.state.id, this.state.userId)
              }
            />
          ) : (
            <BsFillChatQuoteFill />
          )}
        </div>
      </div>
    );
  }
}

export default Comments;
